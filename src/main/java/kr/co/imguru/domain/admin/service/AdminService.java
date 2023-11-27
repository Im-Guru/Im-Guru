package kr.co.imguru.domain.admin.service;

import kr.co.imguru.domain.admin.dto.*;

import java.util.List;

public interface AdminService {

    // Member - CRUD
    List<AdminMemberDto> getAllMembers();

    List<AdminMemberDto> getAllMembersByGuru();

    List<AdminMemberDto> getAllMembersByUser();

    List<AdminMemberDto> getAllMembersBySkill(Long skillId);

    AdminMemberDto getMemberDetail(Long memberId);

    Long updateMember(Long memberId, AdminMemberDto dto);

    void deleteMember(Long memberId);


    // GuruInfo - CRUD
    List<AdminGuruInfoDto> getAllGuruInfos();

    AdminGuruInfoDto getGuruInfoByMember(Long memberId);

    Long updateGuruInfo(Long guruInfoId, AdminGuruInfoDto dto);

    void deleteGuruInfo(Long guruInfoId);


    // Skill
    Long createSKill(AdminSkillDto dto);

    List<AdminSkillDto> getAllSkills();

    AdminSkillDto getSkillDetail(Long skillId);

    Long updateSkill(Long skillId, AdminSkillDto dto);

    void deleteSkill(Long skillId);


    // Post - CRUD
    List<AdminPostDto> getAllPosts();

    List<AdminPostDto> getAllPostsByGuru();

    List<AdminPostDto> getAllPostsByUser();

    List<AdminPostDto> getAllPostsByMember(Long memberId);

    List<AdminPostDto> getAllPostsBySkill(Long skillId);

    AdminPostDto getPostDetail(Long postId);

    Long updatePost(Long postId, AdminPostDto dto);

    void deletePost(Long postId);


    // Reply - CRUD
    List<AdminReplyDto> getAllReplies();

    List<AdminReplyDto> getAllRepliesByPost(Long postId);

    List<AdminReplyDto> getAllRepliesByMember(Long memberId);

    AdminReplyDto getReplyDetail(Long replyId);

    Long updateReply(Long replyId, AdminReplyDto dto);

    void deleteReply(Long replyId);


    // Report Post - CRUD
    List<AdminReportPostDto> getAllReportPosts();

    List<AdminReportPostDto> getAllReportPostsByPost(Long postId);

    AdminReportPostDto getReportPostDetail(Long reportPostId);

    void deleteReportPost(Long reportPostId);


    // Report Reply - CRUD
    List<AdminReportReplyDto> getAllReportReplies();

    List<AdminReportReplyDto> getAllReportRepliesByReply(Long replyId);

    AdminReportReplyDto getReportReplyDetail(Long reportReplyId);

    void deleteReportReply(Long reportReplyId);


    // Review - CRUD
    List<AdminReviewDto> getAllReviews();

    List<AdminReviewDto> getAllReviewsByGuru(Long memberId);    // 리뷰 받은거

    List<AdminReviewDto> getAllReviewByUser(Long memberId);     // 리뷰 쓴거

    Long updateReview(Long reviewId, AdminReviewDto dto);

    void deleteReview(Long reviewId);


    // Message - CRUD



}
