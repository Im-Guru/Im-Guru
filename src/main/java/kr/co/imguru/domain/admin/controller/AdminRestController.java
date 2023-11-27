package kr.co.imguru.domain.admin.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.admin.dto.*;
import kr.co.imguru.domain.admin.service.AdminService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    private final AdminService adminService;

    // Member
    @GetMapping("/member/all")
    public ResponseFormat<List<AdminMemberDto>> readAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMembers());
    }

    @GetMapping("/member/guru")
    public ResponseFormat<List<AdminMemberDto>> readGuruMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMembersByGuru());
    }

    @GetMapping("/member/user")
    public ResponseFormat<List<AdminMemberDto>> readUserMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMembersByUser());
    }

    @GetMapping("/member/skill/{skillId}")
    public ResponseFormat<List<AdminMemberDto>> readMembersBySkill(@PathVariable Long skillId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMembersBySkill(skillId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseFormat<AdminMemberDto> readMemberDetail(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getMemberDetail(memberId));
    }

    @PostMapping("/member/{memberId}")
    public ResponseFormat<Long> updateMember(@PathVariable Long memberId,
                                             @RequestBody @Valid AdminMemberDto memberDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateMember(memberId, memberDto));
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseFormat<Void> deleteMember(@PathVariable Long memberId) {
        adminService.deleteMember(memberId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }


    // GuruInfo
    @GetMapping("/guruInfo/all")
    public ResponseFormat<List<AdminGuruInfoDto>> readAllGuruInfos() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllGuruInfos());
    }

    @GetMapping("/guruInfo/guru/{memberId}")
    public ResponseFormat<AdminGuruInfoDto> readGuruInfoByMember(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getGuruInfoByMember(memberId));
    }

    @PostMapping("/guruInfo/{guruInfoId}")
    public ResponseFormat<Long> updateGuruInfo(@PathVariable Long guruInfoId,
                                               @RequestBody @Valid AdminGuruInfoDto guruInfoDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateGuruInfo(guruInfoId, guruInfoDto));
    }

    @DeleteMapping("/guruInfo/{guruInfoId}")
    public ResponseFormat<Void> deleteGuruInfo(@PathVariable Long guruInfoId) {
        adminService.deleteGuruInfo(guruInfoId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Skill
    @PostMapping("/skill")
    public ResponseFormat<Long> createSkill(@RequestBody @Valid AdminSkillDto skillDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.createSKill(skillDto));
    }

    @GetMapping("/skill/all")
    public ResponseFormat<List<AdminSkillDto>> readAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllSkills());
    }

    @GetMapping("/skill/{skillId}")
    public ResponseFormat<AdminSkillDto> readSkillDetail(@PathVariable Long skillId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getSkillDetail(skillId));
    }

    @PostMapping("/skill/{skillId}")
    public ResponseFormat<Long> updateSkill(@PathVariable Long skillId,
                                            @RequestBody @Valid AdminSkillDto skillDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateSkill(skillId, skillDto));
    }

    @DeleteMapping("/skill/{skillId}")
    public ResponseFormat<Void> deleteSkill(@PathVariable Long skillId) {
        adminService.deleteSkill(skillId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }


    // Post
    @GetMapping("/post/all")
    public ResponseFormat<List<AdminPostDto>> readAllPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllPosts());
    }

    @GetMapping("/post/guru")
    public ResponseFormat<List<AdminPostDto>> readGuruPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllPostsByGuru());
    }

    @GetMapping("/post/user")
    public ResponseFormat<List<AdminPostDto>> readUserPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllPostsByUser());
    }

    @GetMapping("/post/member/{memberId}")
    public ResponseFormat<List<AdminPostDto>> readPostsByMember(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllPostsByMember(memberId));
    }

    @GetMapping("/post/skill/{skillId}")
    public ResponseFormat<List<AdminPostDto>> readPostsBySkill(@PathVariable Long skillId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllPostsBySkill(skillId));
    }

    @GetMapping("/post/{postId}")
    public ResponseFormat<AdminPostDto> readPostsDetail(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getPostDetail(postId));
    }

    @PostMapping("/post/{postId}")
    public ResponseFormat<Long> updatePost(@PathVariable Long postId,
                                           @RequestBody @Valid AdminPostDto postDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updatePost(postId, postDto));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseFormat<Void> deletePost(@PathVariable Long postId) {
        adminService.deletePost(postId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Reply
    @GetMapping("/reply/all")
    public ResponseFormat<List<AdminReplyDto>> readAllReplies() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReplies());
    }

    @GetMapping("/reply/post/{postId}")
    public ResponseFormat<List<AdminReplyDto>> readPostReplies(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllRepliesByPost(postId));
    }

    @GetMapping("/reply/member/{memberId}")
    public ResponseFormat<List<AdminReplyDto>> readMemberReplies(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllRepliesByMember(memberId));
    }

    @GetMapping("/reply/{replyId}")
    public ResponseFormat<AdminReplyDto> readReplyDetail(@PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getReplyDetail(replyId));
    }

    @PostMapping("/reply/{replyId}")
    public ResponseFormat<Long> updateReply(@PathVariable Long replyId,
                                            @RequestBody @Valid AdminReplyDto replyDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateReply(replyId, replyDto));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseFormat<Void> deleteReply(@PathVariable Long replyId) {
        adminService.deleteReply(replyId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Report Post
    @GetMapping("/reportPost/all")
    public ResponseFormat<List<AdminReportPostDto>> readAllReportPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReportPosts());
    }

    @GetMapping("/reportPost/post/{postId}")
    public ResponseFormat<List<AdminReportPostDto>> readPostReportPosts(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReportPostsByPost(postId));
    }

    @GetMapping("/reportPost/{reportPostId}")
    public ResponseFormat<AdminReportPostDto> readReportPostDetail(@PathVariable Long reportPostId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getReportPostDetail(reportPostId));
    }

    @DeleteMapping("/reportPost/{reportPostId}")
    public ResponseFormat<Void> deleteReportPost(@PathVariable Long reportPostId) {
        adminService.deleteReportPost(reportPostId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Report Reply
    @GetMapping("/reportReply/all")
    public ResponseFormat<List<AdminReportReplyDto>> readAllReportReplies() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReportReplies());
    }

    @GetMapping("/reportReply/reply/{replyId}")
    public ResponseFormat<List<AdminReportReplyDto>> readReplyRepostReplies(@PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReportRepliesByReply(replyId));
    }

    @GetMapping("/reportReply/{reportReplyId}")
    public ResponseFormat<AdminReportReplyDto> readReportReplyDetail(@PathVariable Long reportReplyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getReportReplyDetail(reportReplyId));
    }

    @DeleteMapping("/reportReply/{reportReplyId}")
    public ResponseFormat<Void> deleteReportReply(@PathVariable Long reportReplyId) {
        adminService.deleteReportReply(reportReplyId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Review
    @GetMapping("/review/all")
    public ResponseFormat<List<AdminReviewDto>> readAllReviews() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReviews());
    }

    @GetMapping("/review/guru/{memberId}")
    public ResponseFormat<List<AdminReviewDto>> readGuruReviews(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReviewsByGuru(memberId));
    }

    @GetMapping("/review/user/{memberId}")
    public ResponseFormat<List<AdminReviewDto>> readUserReviews(@PathVariable Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReviewByUser(memberId));
    }

    @PostMapping("/review/{reviewId}")
    public ResponseFormat<Long> updateReview(@PathVariable Long reviewId,
                                             @RequestBody @Valid AdminReviewDto reviewDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateReview(reviewId, reviewDto));
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseFormat<Void> deleteReview(@PathVariable Long reviewId) {
        adminService.deleteReview(reviewId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Message

}
