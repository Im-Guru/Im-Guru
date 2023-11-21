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
    public Long createPost(String email, PostCreateDto createDto, List<MultipartFile> files) throws IOException {
//        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getMemberNickname());

        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(member);

        isPostCategory(createDto.getCategoryName());

        Post post = toEntity(member.get().getRole(), createDto, member.get());

        postRepository.save(post);

        // 파일 저장
        if (files != null && !files.isEmpty()) {
            /* 지원하지 않는 확장자 파일 제거 */
            List<MultipartFile> validatedFiles = filesValidation(files);

            /* 걸러진 파일들 업로드 */
            filesUpload(validatedFiles, post.getId());

            /* 유효성 검증을 끝낸 파일들을 하나씩 꺼냄. */
            for (MultipartFile validatedFile : validatedFiles) {
                /* File Entity 생성 후 저장 */
                File file = new File(validatedFile, post);

                fileRepository.save(file);
            }
        }

        return post.getId();
    }

    @Override
    @Transactional
    public PostReadDto getPost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        isPost(post);

        /*File*/
        List<File> fileList = fileRepository.findFileByFileKey("post", postId);
        List<FileFormat> fileFormatList = new ArrayList<>();

        /*파일이 존재한다면*/
        if (fileList != null) {
            for (File file : fileList) {
                FileFormat fileFormat = new FileFormat(file);
                fileFormatList.add(fileFormat);
            }
        }

        updateCntToRedis(postId, "views");

        return toReadDetailDto(post.get(), fileFormatList);
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
    public PostReadDto addLikePostByMemberNickname(Long postId, String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
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
    public List<PostReadDto> getLikePostsByMember(String memberNickname) {
        return postSearchRepository.findLikePostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public PostReadDto updatePost(Long postId, PostUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(updateDto.getMemberNickname());
        isMember(member);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);
        isPost(post);

        isWriter(member, post);

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
    }

    @Override
    @Transactional
    public Page<PostReadDto> searchPostWithPaging(Pageable pageable, String postCategory, String skill, String searchType, String searchText) {
        return postSearchRepository.findWithPaging(pageable, postCategory, skill, searchType, searchText).map(this::toReadDto);
    }





    /*
   Redis에 기록된 정보들을 DB에 업데이트를 진행하면서 데이터의 일관성을 유지하고, Redis의 저장된 정보들을 초기화
   Spring Scheduled를 사용하여 일정 시간마다 실행이 되도록 설정
    */
    @Transactional
    @Scheduled(fixedDelay = 1000L * 180L) // 180초
    public void deleteCntToRedis() {
        String viewKey = "views";
        Set<String> redisKey = redisTemplate.keys("postId*");
        Iterator<String> it = redisKey.iterator();

        while (it.hasNext()) {
            String data = it.next();
            Long postId = Long.parseLong(data.split("::")[1]);

            if (redisTemplate.opsForHash().get(data, viewKey) == null) {
                break;
            } else {
                Long viewCnt = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(data, viewKey)));
                addViewCntFromRedis(postId, viewCnt);
                redisTemplate.opsForHash().delete(data, viewKey);
            }
        }
        System.out.println("Update Complete From Redis");
    }

    private void addViewCntFromRedis(Long postId, Long viewCnt) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        if (!post.isEmpty()) {
            post.get().addViewCnt(viewCnt);

            postRepository.save(post.get());
        }
    }

    /*파일의 유효성 검증*/
    private List<MultipartFile> filesValidation(List<MultipartFile> files) throws IOException {
        /*접근 거부 파일 확장자명*/
        String[] accessDeniedFileExtension = {"exe", "zip"};
        /*접근 거부 파일 컨텐츠 타입*/
        String[] accessDeniedFileContentType = {"application/x-msdos-program", "application/zip"};

        ArrayList<MultipartFile> validatedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            /*원본 파일 이름*/
            String originalFileName = file.getOriginalFilename();
            /*파일의 확장자명*/
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            /*파일의 컨텐츠타입*/
            String fileContentType = file.getContentType();

            /*accessDeniedFileExtension, accessDeniedFileContentType -> 업로드 불가*/
            if (Arrays.asList(accessDeniedFileExtension).contains(fileExtension) ||
                    Arrays.asList(accessDeniedFileContentType).contains(fileContentType)) {
                log.warn(fileExtension + "(" + fileContentType + ") 파일은 지원하지 않는 확장자입니다.");
            } else {/*업로드 가능*/
                validatedFiles.add(file);
            }
        }
        return validatedFiles;
    }

    /*파일 업로드 메소드*/
    private void filesUpload(List<MultipartFile> files, Long postId) throws IOException {
        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        for (MultipartFile file : files) {
            /* 파일 이름 생성 및 수정 */
            String fileName = postId + "_" + file.getOriginalFilename();
            fileName = fileName.replaceAll("\\s", "_"); // 공백을 언더스코어로 대체
            fileName = fileName.replaceAll("[^a-zA-Z0-9_.]", ""); // 영문자, 숫자, 언더스코어, 마침표 이외의 문자 제거

            /* 업로드 경로 */
            java.io.File uploadPath = new java.io.File(rootDir + "/media/post/");
            uploadPath.mkdirs(); // 디렉토리가 존재하지 않으면 생성

            uploadPath = new java.io.File(uploadPath, fileName); // 파일 이름을 포함한 전체 경로

            /* 업로드 */
            file.transferTo(uploadPath);
        }
//        for (MultipartFile file : files) {
//            /*업로드 경로*/
//            java.io.File uploadPath = new java.io.File(rootDir + "/media/" + postId + "_" + file.getOriginalFilename());
//            /*업로드*/
//            file.transferTo(uploadPath);
//        }
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

    private PostReadDto toReadDetailDto(Post post, List<FileFormat> fileFormatList) {
        return PostReadDto.builder()
                .postId(post.getId())
                .memberNickname(post.getMember().getNickname())
                .postCategory(String.valueOf(post.getPostCategory()))
                .title(post.getTitle())
                .content(post.getContent())
                .fileFormat(fileFormatList)
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
                .postCategory(String.valueOf(post.getPostCategory()))
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
