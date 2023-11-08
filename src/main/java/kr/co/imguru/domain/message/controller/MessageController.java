package kr.co.imguru.domain.message.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.message.dto.MessageCreateDto;
import kr.co.imguru.domain.message.dto.MessageMemberDto;
import kr.co.imguru.domain.message.dto.MessageReadDto;
import kr.co.imguru.domain.message.service.MessageService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/message")
    public ResponseFormat<Void> createMessage(@RequestBody @Valid MessageCreateDto createDto) {
        messageService.createMessage(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/message/{memberNickname}")
    public ResponseFormat<List<MessageMemberDto>> readMessageList(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, messageService.getMessageByMember(memberNickname));
    }

    @GetMapping("/message/{sender}/{receiver}")
    public ResponseFormat<List<MessageReadDto>> readDetailMessageList(@PathVariable String sender, @PathVariable String receiver) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, messageService.getDetailMessageByMember(sender, receiver));
    }
}
