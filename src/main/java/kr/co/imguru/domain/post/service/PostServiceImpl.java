package kr.co.imguru.domain.post.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.global.common.PostCategory;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.ForbiddenException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createPost(PostCreateDto createDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getMemberNickname());

        isMember(member);

        isPostCategory(createDto.getCategoryName());

        postRepository.save(toEntity(member.get().getRole(), createDto));
    }

    @Override
    public PostReadDto getPost(Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        isPost(post);

        return toReadDto(post.get());
    }

    @Override
    public List<PostReadDto> getAllGuruPosts() {
        return postRepository.findAllByIsGuruAndIsDeleteFalse(true)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    public List<PostReadDto> getAllPosts() {
        return postRepository.findAllByIsDeleteFalse()
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

        post.get().changeDeleteAt();

        postRepository.save(post.get());
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
            throw new IllegalArgumentException(ResponseStatus.FAIL_ILLEGAL_ACCESS);
        }
    }

    private Post toEntity(Role role, PostCreateDto dto) {
        if (role.getUserRole().equals("도사")) {
            return Post.builder()
                    .member(memberRepository.findByNicknameAndIsDeleteFalse(dto.getMemberNickname()).get())
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
                    .member(memberRepository.findByNicknameAndIsDeleteFalse(dto.getMemberNickname()).get())
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

    private PostReadDto toReadDto(Post post) {
        return PostReadDto.builder()
                .postId(post.getId())
                .memberNickname(post.getMember().getNickname())
                .postCategory(String.valueOf(post.getPostCategory()))
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .isGuru(post.isGuru())
                .viewCnt(post.getViewCnt())
                .likeCnt(post.getLikeCnt())
                .build();
    }

}
