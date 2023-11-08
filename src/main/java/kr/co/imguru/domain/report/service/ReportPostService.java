package kr.co.imguru.domain.report.service;

import kr.co.imguru.domain.report.dto.ReportPostCreateDto;
import kr.co.imguru.domain.report.dto.ReportPostReadDto;

import java.util.List;

public interface ReportPostService {

    void createReportPost(ReportPostCreateDto createDto);

    ReportPostReadDto getReportPost(Long reportPostId);

    List<ReportPostReadDto> getReportPostByPost(Long postId);   // 게시글 신고 목록

    List<ReportPostReadDto> getReportPostByMember(String memberNickname);   // 회원이 신고한 신고 목록

    List<ReportPostReadDto> getAllReportPosts();

}
