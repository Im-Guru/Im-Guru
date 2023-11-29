package kr.co.imguru.domain.message.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.message.dto.MessageCreateDto;
import kr.co.imguru.domain.message.dto.MessageMemberDto;
import kr.co.imguru.domain.message.dto.MessageReadDto;
import kr.co.imguru.domain.message.service.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/message")
    public ResponseFormat<Void> createMessage(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody @Valid MessageCreateDto createDto) {
        messageService.createMessage(userDetails.getUsername(), createDto);
        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @PostMapping("/message/list")
    public ResponseFormat<List<MessageMemberDto>> readMessageList(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, messageService.getMessageByMember(userDetails.getUsername()));
    }

    @GetMapping("/message/{sender}/{receiver}")
    public ResponseFormat<List<MessageReadDto>> readDetailMessageList(@PathVariable String sender, @PathVariable String receiver) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, messageService.getDetailMessageByMember(sender, receiver));
    }
}
