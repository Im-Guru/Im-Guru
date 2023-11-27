package kr.co.imguru.domain.admin.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.admin.dto.*;
import kr.co.imguru.domain.guru.entity.GuruInfo;
import kr.co.imguru.domain.guru.repository.GuruInfoRepository;
import kr.co.imguru.domain.guru.repository.GuruInfoSearchRepository;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.post.repository.PostSearchRepository;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.domain.reply.repository.ReplyRepository;
import kr.co.imguru.domain.reply.repository.ReplySearchRepository;
import kr.co.imguru.domain.report.entity.ReportPost;
import kr.co.imguru.domain.report.entity.ReportReply;
import kr.co.imguru.domain.report.repository.ReportPostRepository;
import kr.co.imguru.domain.report.repository.ReportPostSearchRepository;
import kr.co.imguru.domain.report.repository.ReportReplyRepository;
import kr.co.imguru.domain.report.repository.ReportReplySearchRepository;
import kr.co.imguru.domain.review.entity.Review;
import kr.co.imguru.domain.review.repository.ReviewRepository;
import kr.co.imguru.domain.review.repository.ReviewSearchRepository;
import kr.co.imguru.domain.skill.entity.Skill;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final GuruInfoRepository guruInfoRepository;

    private final GuruInfoSearchRepository guruInfoSearchRepository;

    private final SkillRepository skillRepository;

    private final PostRepository postRepository;

    private final PostSearchRepository postSearchRepository;

    private final ReplyRepository replyRepository;

    private final ReplySearchRepository replySearchRepository;

    private final ReportPostRepository reportPostRepository;

    private final ReportPostSearchRepository reportPostSearchRepository;

    private final ReportReplyRepository reportReplyRepository;

    private final ReportReplySearchRepository reportReplySearchRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewSearchRepository reviewSearchRepository;


    @Override
    @Transactional
    public List<AdminMemberDto> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toMemberDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminMemberDto> getAllMembersByGuru() {
        return memberRepository.findAllByRole(Role.ROLE_GURU)
                .stream()
                .map(this::toMemberDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminMemberDto> getAllMembersByUser() {
        return memberRepository.findAllByRole(Role.ROLE_USER)
                .stream()
                .map(this::toMemberDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminMemberDto> getAllMembersBySkill(Long skillId) {
        return memberRepository.findAllBySkillId(skillId)
                .stream()
                .map(this::toMemberDto)
                .toList();
    }

    @Override
    @Transactional
    public AdminMemberDto getMemberDetail(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        isMember(member);

        return toMemberDto(member.get());
    }

    @Override
    @Transactional
    public Long updateMember(Long memberId, AdminMemberDto dto) {
        Optional<Member> member = memberRepository.findById(memberId);
        isMember(member);

        member.get().changeMemberByAdmin(dto, passwordEncoder.encode(dto.getPassword()),
                skillRepository.findByNameAndIsDeleteFalse(dto.getSkillName()).get());

        memberRepository.save(member.get());

        return memberId;
    }

    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        isMember(member);

        memberRepository.delete(member.get());
    }

    @Override
    public List<AdminGuruInfoDto> getAllGuruInfos() {
        return guruInfoRepository.findAll()
                .stream()
                .map(this::toGuruInfoDto)
                .toList();
    }

    @Override
    public AdminGuruInfoDto getGuruInfoByMember(Long memberId) {
        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByMemberId(memberId);

        isGuruInfo(guruInfo);

        return toGuruInfoDto(guruInfo.get());
    }

    @Override
    public Long updateGuruInfo(Long guruInfoId, AdminGuruInfoDto dto) {
        Optional<GuruInfo> guruInfo = guruInfoRepository.findById(guruInfoId);
        isGuruInfo(guruInfo);

        guruInfo.get().changeGuruInfoByAdmin(dto);

        guruInfoRepository.save(guruInfo.get());

        return guruInfoId;
    }

    @Override
    public void deleteGuruInfo(Long guruInfoId) {
        Optional<GuruInfo> guruInfo = guruInfoRepository.findById(guruInfoId);

        isGuruInfo(guruInfo);

        guruInfoRepository.delete(guruInfo.get());
    }

    @Override
    @Transactional
    public Long createSKill(AdminSkillDto dto) {
        isSkillName(dto.getName());

        Skill skill = toSkillEntity(dto);

        skillRepository.save(skill);

        return skill.getId();
    }

    @Override
    @Transactional
    public List<AdminSkillDto> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::toSkillDto)
                .toList();
    }

    @Override
    public AdminSkillDto getSkillDetail(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);

        isSkill(skill);

        return toSkillDto(skill.get());
    }

    @Override
    @Transactional
    public Long updateSkill(Long skillId, AdminSkillDto dto) {
        Optional<Skill> skill = skillRepository.findById(skillId);

        isSkill(skill);

        skill.get().changeSkillByAdmin(dto);

        skillRepository.save(skill.get());

        return skillId;
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId) {
        Optional<Skill> skill = skillRepository.findById(skillId);

        isSkill(skill);

        skillRepository.delete(skill.get());
    }

    @Override
    @Transactional
    public List<AdminPostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminPostDto> getAllPostsByGuru() {
        return postSearchRepository.findPostsByMemberGuru()
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminPostDto> getAllPostsByUser() {
        return postSearchRepository.findPostsByMemberUser()
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminPostDto> getAllPostsByMember(Long memberId) {
        return postSearchRepository.findPostsByMemberId(memberId)
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminPostDto> getAllPostsBySkill(Long skillId) {
        return postSearchRepository.findPostsBySkill(skillId)
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    @Override
    @Transactional
    public AdminPostDto getPostDetail(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        isPost(post);

        return toPostDto(post.get());
    }

    @Override
    @Transactional
    public Long updatePost(Long postId, AdminPostDto dto) {
        Optional<Post> post = postRepository.findById(postId);

        isPost(post);

        post.get().changePost(dto);

        postRepository.save(post.get());

        return postId;
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        isPost(post);

        postRepository.delete(post.get());
    }

    @Override
    @Transactional
    public List<AdminReplyDto> getAllReplies() {
        return replyRepository.findAll()
                .stream()
                .map(this::toReplyDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReplyDto> getAllRepliesByPost(Long postId) {
        return replySearchRepository.findRepliesByPost(postId)
                .stream()
                .map(this::toReplyDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReplyDto> getAllRepliesByMember(Long memberId) {
        return replySearchRepository.findRepliesByMember(memberId)
                .stream()
                .map(this::toReplyDto)
                .toList();
    }

    @Override
    @Transactional
    public AdminReplyDto getReplyDetail(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);

        isReply(reply);

        return toReplyDto(reply.get());
    }

    @Override
    @Transactional
    public Long updateReply(Long replyId, AdminReplyDto dto) {
        Optional<Reply> reply = replyRepository.findById(replyId);

        isReply(reply);

        reply.get().changeReplyByAdmin(dto);

        replyRepository.save(reply.get());

        return replyId;
    }

    @Override
    @Transactional
    public void deleteReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);

        isReply(reply);

        replyRepository.delete(reply.get());
    }

    @Override
    @Transactional
    public List<AdminReportPostDto> getAllReportPosts() {
        return reportPostRepository.findAll()
                .stream()
                .map(this::toReportPostDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReportPostDto> getAllReportPostsByPost(Long postId) {
        return reportPostSearchRepository.findReportPostsByPost(postId)
                .stream()
                .map(this::toReportPostDto)
                .toList();
    }

    @Override
    @Transactional
    public AdminReportPostDto getReportPostDetail(Long reportPostId) {
        Optional<ReportPost> reportPost = reportPostRepository.findById(reportPostId);

        isReportPost(reportPost);

        return toReportPostDto(reportPost.get());
    }

    @Override
    @Transactional
    public void deleteReportPost(Long reportPostId) {
        Optional<ReportPost> reportPost = reportPostRepository.findById(reportPostId);

        isReportPost(reportPost);

        reportPostRepository.delete(reportPost.get());
    }

    @Override
    @Transactional
    public List<AdminReportReplyDto> getAllReportReplies() {
        return reportReplyRepository.findAll()
                .stream()
                .map(this::toReportReplyDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReportReplyDto> getAllReportRepliesByReply(Long replyId) {
        return reportReplySearchRepository.findReportRepliesByReply(replyId)
                .stream()
                .map(this::toReportReplyDto)
                .toList();
    }

    @Override
    @Transactional
    public AdminReportReplyDto getReportReplyDetail(Long reportReplyId) {
        Optional<ReportReply> reportReply = reportReplyRepository.findById(reportReplyId);

        isReportReply(reportReply);

        return toReportReplyDto(reportReply.get());
    }

    @Override
    @Transactional
    public void deleteReportReply(Long reportReplyId) {
        Optional<ReportReply> reportReply = reportReplyRepository.findById(reportReplyId);

        isReportReply(reportReply);

        reportReplyRepository.delete(reportReply.get());
    }

    @Override
    @Transactional
    public List<AdminReviewDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::toReviewDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReviewDto> getAllReviewsByGuru(Long memberId) {
        return reviewSearchRepository.findReviewsGuruByMember(memberId)
                .stream()
                .map(this::toReviewDto)
                .toList();
    }

    @Override
    @Transactional
    public List<AdminReviewDto> getAllReviewByUser(Long memberId) {
        return reviewSearchRepository.findReviewsUserByMember(memberId)
                .stream()
                .map(this::toReviewDto)
                .toList();
    }

    @Override
    @Transactional
    public Long updateReview(Long reviewId, AdminReviewDto dto) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        isReview(review);

        review.get().changeReviewByAdmin(dto);

        reviewRepository.save(review.get());

        return reviewId;
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        isReview(review);

        reviewRepository.delete(review.get());
    }






    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NOT_FOUND);
        }
    }

    private void isSkillName(String skillName) {
        if (skillRepository.existsByNameAndIsDeleteFalse(skillName)) {
            throw new DuplicatedException(ResponseStatus.FAIL_SKILL_NAME_DUPLICATED);
        }
    }

    private void isGuruInfo(Optional<GuruInfo> guruInfo) {
        if (guruInfo.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_GURU_INFO_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if(reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
        }
    }

    private void isReportPost(Optional<ReportPost> reportPost) {
        if (reportPost.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_POST_NOT_FOUND);
        }
    }

    private void isReportReply(Optional<ReportReply> reportReply) {
        if (reportReply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_REPLY_NOT_FOUND);
        }
    }

    private void isReview(Optional<Review> review) {
        if (review.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REVIEW_NOT_FOUND);
        }
    }


    private AdminMemberDto toMemberDto(Member member) {
        return AdminMemberDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .job(member.getJob())
                .zoneCode(member.getZoneCode())
                .roadAddress(member.getRoadAddress())
                .detailAddress(member.getDetailAddress())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(member.getSkill().getName())
                .isDelete(member.isDelete())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();
    }

    private AdminGuruInfoDto toGuruInfoDto(GuruInfo guruInfo) {
        return AdminGuruInfoDto.builder()
                .guruInfoId(guruInfo.getId())
                .memberNickname(guruInfo.getMember().getNickname())
                .intro(guruInfo.getIntro())
                .companyName(guruInfo.getCompanyName())
                .position(guruInfo.getPosition())
                .careerAt(guruInfo.getCareerAt())
                .contactTime(guruInfo.getContactTime())
                .workArea(guruInfo.getWorkArea())
                .description(guruInfo.getDescription())
                .isDelete(guruInfo.isDelete())
                .regDate(guruInfo.getRegDate())
                .modDate(guruInfo.getModDate())
                .build();
    }

    private AdminSkillDto toSkillDto(Skill skill) {
        return AdminSkillDto.builder()
                .skillId(skill.getId())
                .name(skill.getName())
                .isDelete(skill.isDelete())
                .regDate(skill.getRegDate())
                .modDate(skill.getModDate())
                .build();
    }

    private Skill toSkillEntity(AdminSkillDto dto) {
        return Skill.builder()
                .name(dto.getName())
                .build();
    }

    private AdminPostDto toPostDto(Post post) {
        return AdminPostDto.builder()
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
                .isDelete(post.isDelete())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .build();
    }

    private AdminReplyDto toReplyDto(Reply reply) {
        return AdminReplyDto.builder()
                .replyId(reply.getId())
                .memberNickname(reply.getMember().getNickname())
                .postId(reply.getPost().getId())
                .postTitle(reply.getPost().getTitle())
                .content(reply.getContent())
                .likeCnt(reply.getLikeCnt())
                .memberSkill(reply.getMember().getSkill().getName())
                .isDelete(reply.isDelete())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }

    private AdminReportPostDto toReportPostDto(ReportPost reportPost) {
        return AdminReportPostDto.builder()
                .reportPostId(reportPost.getId())
                .memberNickname(reportPost.getMember().getNickname())
                .postId(reportPost.getPost().getId())
                .postTitle(reportPost.getPost().getTitle())
                .postContent(reportPost.getPost().getContent())
                .postWriter(reportPost.getPost().getMember().getNickname())
                .categoryName(String.valueOf(reportPost.getReportCategory()))
                .description(reportPost.getDescription())
                .isDelete(reportPost.isDelete())
                .regDate(reportPost.getRegDate())
                .modDate(reportPost.getModDate())
                .build();
    }

    private AdminReportReplyDto toReportReplyDto(ReportReply reportReply) {
        return AdminReportReplyDto.builder()
                .reportReplyId(reportReply.getId())
                .memberNickname(reportReply.getMember().getNickname())
                .replyId(reportReply.getReply().getId())
                .replyContent(reportReply.getReply().getContent())
                .replyWriter(reportReply.getReply().getMember().getNickname())
                .categoryName(String.valueOf(reportReply.getReportCategory()))
                .description(reportReply.getDescription())
                .isDelete(reportReply.isDelete())
                .regDate(reportReply.getRegDate())
                .modDate(reportReply.getModDate())
                .build();
    }

    private AdminReviewDto toReviewDto(Review review) {
        return AdminReviewDto.builder()
                .reviewId(review.getId())
                .userNickname(review.getUser().getNickname())
                .guruNickname(review.getGuru().getNickname())
                .guruSkill(review.getGuru().getSkill().getName())
                .content(review.getContent())
                .rate(review.getRate())
                .likeCnt(review.getLikeCnt())
                .isDelete(review.isDelete())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
    }

}
