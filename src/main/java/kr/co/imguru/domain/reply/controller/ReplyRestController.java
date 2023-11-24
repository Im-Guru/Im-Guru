package kr.co.imguru.domain.reply.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.reply.dto.ReplyCreateDto;
import kr.co.imguru.domain.reply.dto.ReplyReadDto;
import kr.co.imguru.domain.reply.dto.ReplyUpdateDto;
import kr.co.imguru.domain.reply.service.ReplyService;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReplyRestController {

    private final ReplyService replyService;

    //Create
    @PostMapping("/reply/{postId}")
    public ResponseFormat<Long> createReply(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long postId,
                                            @RequestBody @Valid ReplyCreateDto createDto) {

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.createReply(userDetails.getUsername(), postId, createDto));
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

    @PostMapping("/reply/like/{replyId}")
    public ResponseFormat<ReplyReadDto> addLikeReply(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.addLikeReply(userDetails.getUsername(), replyId));
    }

    @GetMapping("/reply/like/{memberNickname}")
    public ResponseFormat<List<ReplyReadDto>> readLikeRepliesByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getLikeRepliesByMember(memberNickname));
    }

    //Update
    @PutMapping("/reply/{replyId}")
    public ResponseFormat<ReplyReadDto> updateReply(@PathVariable Long replyId,
                                                    @RequestBody @Valid ReplyUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.updateReply(replyId, updateDto));
    }

    @DeleteMapping("/reply/{postId}/{replyId}")
    public ResponseFormat<Long> deleteReply(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long postId,
                                            @PathVariable Long replyId) {

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.deleteReply(userDetails.getUsername(), postId, replyId));
    }

    @PostMapping("/reply/myWrite")
    public ResponseFormat<List<ReplyReadDto>> getRepliesByLoginMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getRepliesByLoginMember(userDetails.getUsername()));
    }

    @PostMapping("/reply/member/{memberNickname}")
    public ResponseFormat<List<ReplyReadDto>> getRepliesByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, replyService.getRepliesByMemberNickname(memberNickname));
    }

}
