package kr.co.imguru.domain.reply.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.reply.dto.ReplyCreateDto;
import kr.co.imguru.domain.reply.dto.ReplyReadDto;
import kr.co.imguru.domain.reply.dto.ReplyUpdateDto;
import kr.co.imguru.domain.reply.service.ReplyService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReplyRestController {

    private final ReplyService replyService;

    //Create
    @PostMapping("/reply")
    public ResponseFormat<Void> createReply(@RequestBody @Valid ReplyCreateDto createDto) {
        replyService.createReply(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    //Read One
    @GetMapping("/reply/{replyId}")
    public ResponseFormat<ReplyReadDto> readReply(@PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getReply(replyId));
    }

    //ReadAll
    @GetMapping("/reply/all")
    public ResponseFormat<List<ReplyReadDto>> readAllReplies() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getAllReplies());
    }

    @GetMapping("/reply/post/{postId}")
    public ResponseFormat<List<ReplyReadDto>> readRepliesByPost(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getRepliesByPost(postId));
    }

    @GetMapping("/reply/member/{memberNickname}")
    public ResponseFormat<List<ReplyReadDto>> readRepliesByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getRepliesByMember(memberNickname));
    }

    //Update
    @PutMapping("/reply/{replyId}")
    public ResponseFormat<ReplyReadDto> updateReply(@PathVariable Long replyId,
                                                    @RequestBody @Valid ReplyUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.updateReply(replyId, updateDto));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseFormat<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}
