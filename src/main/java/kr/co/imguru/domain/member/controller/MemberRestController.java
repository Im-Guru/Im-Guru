package kr.co.imguru.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.service.MemberService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberRestController {

    private final MemberService memberService;

    // Create
    @PostMapping("/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberCreateDto createDto) {
        memberService.createMember(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @PostMapping("/member/guru")
    public ResponseFormat<Void> createGuruMember(@RequestBody @Valid MemberCreateDto createDto) {
        memberService.createGuruMember(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    // Read One
    @GetMapping("/member/{memberNickname}")
    public ResponseFormat<MemberReadDto> readMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMember(memberNickname));
    }

    // Read All
    @GetMapping("/member/all")
    public ResponseFormat<List<MemberReadDto>> readAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

    // Read All User
    @GetMapping("/member/user")
    public ResponseFormat<List<MemberReadDto>> readAllUserMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllUserMembers());
    }

    // Read All Guru
    @GetMapping("/member/guru")
    public ResponseFormat<List<MemberReadDto>> readAllGuruMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllGuruMembers());
    }


    // Update
    @PutMapping("/member/{memberNickname}")
    public ResponseFormat<MemberReadDto> updateMember(@PathVariable String memberNickname,
                             @RequestBody @Valid MemberUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.updateMember(memberNickname, updateDto));
    }

    // Delete
    @DeleteMapping("/member/{memberNickname}")
    public ResponseFormat<Void> deleteMember(@PathVariable String memberNickname) {
        memberService.deleteMember(memberNickname);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

}
