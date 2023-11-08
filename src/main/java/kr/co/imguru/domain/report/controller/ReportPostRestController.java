package kr.co.imguru.domain.report.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.report.dto.ReportPostCreateDto;
import kr.co.imguru.domain.report.dto.ReportPostReadDto;
import kr.co.imguru.domain.report.service.ReportPostService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportPostRestController {

    private final ReportPostService reportPostService;

    @PostMapping("/reportPost")
    public ResponseFormat<Void> createReportPost(@RequestBody @Valid ReportPostCreateDto createDto) {
        reportPostService.createReportPost(createDto);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/reportPost/{reportPostId}")
    public ResponseFormat<ReportPostReadDto> readReportPost(@PathVariable Long reportPostId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportPostService.getReportPost(reportPostId));
    }

    @GetMapping("/reportPost/all")
    public ResponseFormat<List<ReportPostReadDto>> readAllReportPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportPostService.getAllReportPosts());
    }

    @GetMapping("/reportPost/post/{postId}")
    public ResponseFormat<List<ReportPostReadDto>> readReportPostsByPost(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportPostService.getReportPostByPost(postId));
    }

    @GetMapping("/reportPost/member/{memberNickname}")
    public ResponseFormat<List<ReportPostReadDto>> readReportPostsByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportPostService.getReportPostByMember(memberNickname));
    }
}
