package kr.co.imguru.domain.post.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.file.entity.File;
import kr.co.imguru.domain.file.entity.FileFormat;
import kr.co.imguru.domain.file.repository.FileRepository;
import kr.co.imguru.domain.like.entity.LikePost;
import kr.co.imguru.domain.like.repository.LikePostRepository;
import kr.co.imguru.domain.like.repository.LikePostSearchRepository;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.post.repository.PostSearchRepository;
import kr.co.imguru.global.common.PostCategory;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.ForbiddenException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final LikePostRepository likePostRepository;

    private final FileRepository fileRepository;

    private final PostSearchRepository postSearchRepository;

    private final LikePostSearchRepository likePostSearchRepository;

    private final RedisTemplate<String, Object> redisTemplate; // RedisTemplate 주입

    @Override
    @Transactional
    public Long createPost(String email, PostCreateDto createDto) {

        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(member);

        isPostCategory(createDto.getCategoryName());

        Post post = toEntity(member.get().getRole(), createDto, member.get());

        postRepository.save(post);

        return post.getId();
    }

    @Override
    @Transactional
    public PostReadDto getPost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        isPost(post);

        updateCntToRedis(postId, "views");

        /*Post File*/
        List<File> fileList = fileRepository.findFileByFileKey("post", postId);

        Optional<File> memberImage = fileRepository.findOneFileByFileKey("member", post.get().getMember().getId());
        if (memberImage.isEmpty()) {
            return toReadDetailDto(post.get(), fileList, null);
        } else {
            FileFormat memberImageFormat = new FileFormat(memberImage.get());
            return toReadDetailDto(post.get(), fileList, memberImageFormat);
        }

    }

    @Override
    @Transactional
    public List<PostReadDto> getAllGuruPosts() {
        return postRepository.findAllByIsGuruAndIsDeleteFalse(true)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<PostReadDto> getAllPosts() {
        return postRepository.findAllByIsDeleteFalse()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<PostReadDto> getPostsByMember(String memberNickname) {
        return postSearchRepository.findPostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<PostReadDto> getLikePostsByMember(String memberNickname) {
        return postSearchRepository.findLikePostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public PostReadDto updatePost(String email, Long postId, PostUpdateDto updateDto) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        isWriter(loginMember, post);

        isPostCategory(updateDto.getCategoryName());

        post.get().changePost(updateDto);

        postRepository.save(post.get());

        return toReadDto(post.get());
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        isPost(post);

        /*해당 boardId를 가지고 있는 file 먼저 삭제*/
        fileRepository.deleteFileByFileKey("post", postId);

        post.get().changeDeleteAt();

        postRepository.save(post.get());
    }


    /*
    게시글 상세조회 요청 시, 해당 postId에 해당하는 viewCnt를 +1 해준 값을 Redis에 저장
     */
    @Override
    @Transactional
    public void updateCntToRedis(Long postId, String hashKey) {

        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();

        String key = "postId::" + postId;

        try {
            if (hashOperations.get(key, hashKey) == null) {
                if (hashKey.equals("views")) {
                    hashOperations.put(key, hashKey, postRepository.findByIdAndIsDeleteFalse(postId).get().getViewCnt());
                }
                hashOperations.increment(key, hashKey, 1L);
                System.out.println("hashOperations.get is null ---- " + hashOperations.get(key, hashKey));
            } else {
                hashOperations.increment(key, hashKey, 1L);
                System.out.println("hashOperations.get is not null ---- " + hashOperations.get(key, hashKey));
            }
        } catch (Exception e) {
            // 예외 처리 (예: 로그 출력 등)
            e.printStackTrace();
            // 예외 처리에 따라 다른 동작을 추가할 수 있습니다.
        }
    }

    @Override
    @Transactional
    public Page<PostReadDto> searchPostWithPaging(Pageable pageable, String postCategory, String skill, String role, String searchType, String searchText) {
        return postSearchRepository.findWithPaging(pageable, postCategory, skill, role, searchType, searchText).map(this::toReadDto);
    }

    @Override
    @Transactional
    public PostReadDto addPostLike(String email, Long postId) {
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(member);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        isLikePostDuplicated(postId, member.get().getId());

        LikePost create = LikePost.builder()
                .member(member.get())
                .post(post.get())
                .build();

        likePostRepository.save(create);

        post.get().addLikeCnt();

        postRepository.save(post.get());

        return toReadDto(post.get());
    }


    @Override
    @Transactional
    public List<PostReadDto> getPostsByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(loginMember);

        return postSearchRepository.findPostsByMemberNickname(loginMember.get().getNickname())
                .stream()
                .map(this::toReadDto)
                .toList();

    }

    @Override
    @Transactional
    public List<PostReadDto> getLikePostsByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(loginMember);

        return postSearchRepository.findLikePostsByMemberNickname(loginMember.get().getNickname())
                .stream()
                .map(this::toReadDto)
                .toList();

    }

    @Override
    @Transactional
    public List<PostReadDto> getPostsByMemberNickname(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        return postSearchRepository.findPostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();

    }

    @Override
    @Transactional
    public List<PostReadDto> getLikePostsByMemberNickname(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        return postSearchRepository.findLikePostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }


    /*
   Redis에 기록된 정보들을 DB에 업데이트를 진행하면서 데이터의 일관성을 유지하고, Redis의 저장된 정보들을 초기화
   Spring Scheduled를 사용하여 일정 시간마다 실행이 되도록 설정
    */
    @Transactional
    @Scheduled(fixedDelay = 1000L * 180L) // 180초
    public void deleteCntToRedis() {

        try {
            String viewKey = "views";
            Set<String> redisKeys = redisTemplate.keys("postId*");

            for (String key : redisKeys) {
                Long postId = Long.parseLong(key.split("::")[1]);

                Object viewCntObject = redisTemplate.opsForHash().get(key, viewKey);

                if (viewCntObject != null) {
                    Long viewCnt = Long.parseLong(String.valueOf(viewCntObject));
                    try {
                        addViewCntFromRedis(postId, viewCnt);
                        redisTemplate.opsForHash().delete(key, viewKey);
                    } catch (Exception e) {
                        // 예외 처리 (예: 오류 기록)
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Redis에서 업데이트 완료");
        } catch (RedisConnectionFailureException e) {
            // Redis에 연결할 수 없는 경우 예외를 무시하고 계속 진행
            System.err.println("Unable to connect to Redis. The application will continue running without Redis.");
            e.printStackTrace();
        }

    }

    private void addViewCntFromRedis(Long postId, Long viewCnt) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        if (!post.isEmpty()) {
            post.get().addViewCnt(viewCnt);

            postRepository.save(post.get());
        }
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isWriter(Optional<Member> member, Optional<Post> post) {
        if (!member.get().getNickname().equals(post.get().getMember().getNickname())) {
            throw new ForbiddenException(ResponseStatus.FAIL_POST_WRITER_NOT_MATCH);
        }
    }

    private void isPostCategory(String categoryName) {
        try {
            PostCategory.valueOf(categoryName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_POST_CATEGORY_NOT_FOUND);
        }
    }

    private void isLikePostDuplicated(Long postId, Long memberId) {
        Optional<LikePost> likePost = Optional.ofNullable(likePostSearchRepository.existsByPostIdAndMemberId(postId, memberId));

        if (likePost.isPresent()) {
            throw new DuplicatedException(ResponseStatus.FAIL_POST_LIKE_DUPLICATED);
        }
    }

    private Post toEntity(Role role, PostCreateDto dto, Member member) {
        if (role.getUserRole().equals("도사")) {
            return Post.builder()
                    .member(member)
                    .postCategory(PostCategory.valueOf(dto.getCategoryName()))
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .price(dto.getPrice())
                    .isGuru(true)
                    .likeCnt(0L)
                    .viewCnt(0L)
                    .build();
        } else {
            return Post.builder()
                    .member(member)
                    .postCategory(PostCategory.valueOf(dto.getCategoryName()))
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .price("0")
                    .isGuru(false)
                    .likeCnt(0L)
                    .viewCnt(0L)
                    .build();
        }
    }

    private PostReadDto toReadDetailDto(Post post, List<File> fileList, FileFormat memberImage) {
        return PostReadDto.builder()
                .postId(post.getId())
                .memberNickname(post.getMember().getNickname())
                .memberImage(memberImage)
                .postCategory(String.valueOf(post.getPostCategory().getDisplayValue()))
                .title(post.getTitle())
                .content(post.getContent())
                .fileList(fileList)
                .price(post.getPrice())
                .isGuru(post.isGuru())
                .skillName(post.getMember().getSkill().getName())
                .replyCnt(postRepository.countRepliesByPostId(post.getId()))
                .viewCnt(post.getViewCnt())
                .likeCnt(post.getLikeCnt())
                .regDate(post.getRegDate())
                .build();
    }

    private PostReadDto toReadDto(Post post) {
        return PostReadDto.builder()
                .postId(post.getId())
                .memberNickname(post.getMember().getNickname())
                .postCategory(String.valueOf(post.getPostCategory().getDisplayValue()))
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .isGuru(post.isGuru())
                .skillName(post.getMember().getSkill().getName())
                .replyCnt(postRepository.countRepliesByPostId(post.getId()))
                .viewCnt(post.getViewCnt())
                .likeCnt(post.getLikeCnt())
                .regDate(post.getRegDate())
                .build();
    }

}
