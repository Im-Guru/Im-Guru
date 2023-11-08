package kr.co.imguru.domain.report.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.report.dto.ReportReplyCreateDto;
import kr.co.imguru.domain.report.dto.ReportReplyReadDto;
import kr.co.imguru.domain.report.service.ReportReplyService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportReplyRestController {

    private final ReportReplyService reportReplyService;

    @PostMapping("/reportReply")
    public ResponseFormat<Void> createReportReply(@RequestBody @Valid ReportReplyCreateDto createDto) {
        reportReplyService.createReportReply(createDto);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/reportReply/{reportReplyId}")
    public ResponseFormat<ReportReplyReadDto> readReportReply(@PathVariable Long reportReplyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportReplyService.getReportReply(reportReplyId));
    }

    @GetMapping("/reportReply/all")
    public ResponseFormat<List<ReportReplyReadDto>> readAllReportReplies() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportReplyService.getAllReportReplies());
    }

    @GetMapping("/reportReply/reply/{replyId}")
    public ResponseFormat<List<ReportReplyReadDto>> readReportRepliesByReply(@PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportReplyService.getReportRepliesByReply(replyId));
    }

    @GetMapping("/reportReply/member/{memberNickname}")
    public ResponseFormat<List<ReportReplyReadDto>> readReportRepliesByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportReplyService.getReportRepliesByMember(memberNickname));
    }

}
