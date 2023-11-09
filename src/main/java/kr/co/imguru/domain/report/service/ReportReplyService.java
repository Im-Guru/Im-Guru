package kr.co.imguru.domain.report.service;

import kr.co.imguru.domain.report.dto.ReportReplyCreateDto;
import kr.co.imguru.domain.report.dto.ReportReplyReadDto;

import java.util.List;

public interface ReportReplyService {

    void createReportReply(ReportReplyCreateDto createDto);

    ReportReplyReadDto getReportReply(Long reportReplyId);

    List<ReportReplyReadDto> getReportRepliesByReply(Long replyId);   // 댓글 신고 목록

    List<ReportReplyReadDto> getReportRepliesByMember(String memberNickname);   // 회원이 신고한 신고 목록

    List<ReportReplyReadDto> getAllReportReplies();
}
