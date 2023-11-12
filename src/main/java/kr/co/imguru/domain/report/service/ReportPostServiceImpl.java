package kr.co.imguru.domain.report.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.guru.entity.GuruInfo;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.report.dto.ReportPostCreateDto;
import kr.co.imguru.domain.report.dto.ReportPostReadDto;
import kr.co.imguru.domain.report.entity.ReportPost;
import kr.co.imguru.domain.report.repository.ReportPostRepository;
import kr.co.imguru.domain.report.repository.ReportPostSearchRepository;
import kr.co.imguru.global.common.ReportCategory;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportPostServiceImpl implements ReportPostService {

    private final ReportPostRepository reportPostRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReportPostSearchRepository reportPostSearchRepository;

    @Override
    @Transactional
    public void createReportPost(ReportPostCreateDto createDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(createDto.getMemberNickname());
        isMember(member);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(createDto.getPostId());
        isPost(post);

        isReportCategory(createDto.getCategoryName());

        isReportPostDuplicated(createDto.getPostId(), createDto.getMemberNickname());

        reportPostRepository.save(toEntity(createDto));
    }

    @Override
    @Transactional
    public ReportPostReadDto getReportPost(Long reportPostId) {
        Optional<ReportPost> reportPost = reportPostRepository.findById(reportPostId);

        isReportPost(reportPost);

        return toReadDto(reportPost.get());
    }

    @Override
    @Transactional
    public List<ReportPostReadDto> getReportPostByPost(Long postId) {
        return reportPostSearchRepository.findReportPostsByPostId(postId)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ReportPostReadDto> getReportPostByMember(String memberNickname) {
        return reportPostSearchRepository.findReportPostsByMemberNickname(memberNickname)
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ReportPostReadDto> getAllReportPosts() {
        return reportPostRepository.findAll()
                .stream()
                .map(this::toReadDto)
                .toList();
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

    private void isReportCategory(String categoryName) {
        try {
            ReportCategory.valueOf(categoryName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_REPORT_CATEGORY_NOT_FOUND);
        }
    }

    private void isReportPost(Optional<ReportPost> reportPost) {
        if (reportPost.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_POST_NOT_FOUND);
        }
    }

    private void isReportPostDuplicated(Long postId, String memberNickname) {
        Optional<ReportPost> reportPost = Optional.ofNullable(reportPostSearchRepository.existsByPostIdAndMemberNickname(postId, memberNickname));

        if (reportPost.isPresent()) {
            throw new DuplicatedException(ResponseStatus.FAIL_REPORT_DUPLICATED);
        }
    }

    private ReportPost toEntity(ReportPostCreateDto dto) {
        return ReportPost.builder()
                .member(memberRepository.findByNicknameAndIsDeleteFalse(dto.getMemberNickname()).get())
                .post(postRepository.findByIdAndIsDeleteFalse(dto.getPostId()).get())
                .reportCategory(ReportCategory.valueOf(dto.getCategoryName()))
                .description(dto.getDescription())
                .isAccept(false)
                .build();
    }

    private ReportPostReadDto toReadDto(ReportPost reportPost) {
        return ReportPostReadDto.builder()
                .reportPostId(reportPost.getId())
                .memberNickname(reportPost.getMember().getNickname())
                .postId(reportPost.getPost().getId())
                .postTitle(reportPost.getPost().getTitle())
                .postContent(reportPost.getPost().getContent())
                .postWriter(reportPost.getPost().getMember().getNickname())
                .categoryName(String.valueOf(reportPost.getReportCategory()))
                .description(reportPost.getDescription())
                .build();
    }

}
