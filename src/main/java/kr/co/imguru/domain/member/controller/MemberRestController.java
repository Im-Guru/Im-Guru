package kr.co.imguru.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.service.MemberService;
import kr.co.imguru.global.auth.TokenDto;
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

    @PostMapping("/join/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberCreateDto createDto) {
        memberService.createMember(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @PostMapping("/join/guru")
    public ResponseFormat<Void> createGuruMember(@RequestBody @Valid MemberCreateDto createDto) {
        memberService.createGuruMember(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @PostMapping("/login")
    public ResponseFormat<MemberReadDto> loginMember(@RequestBody @Valid MemberLoginDto loginDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.loginMember(loginDto));
    }

    @GetMapping("/member/{memberNickname}")
    public ResponseFormat<MemberReadDto> readMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMember(memberNickname));
    }

    @GetMapping("/member/all")
    public ResponseFormat<List<MemberReadDto>> readAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

    @GetMapping("/member/user")
    public ResponseFormat<List<MemberReadDto>> readAllUserMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllUserMembers());
    }

    @GetMapping("/member/guru")
    public ResponseFormat<List<MemberReadDto>> readAllGuruMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllGuruMembers());
    }

    @PutMapping("/member/{memberNickname}")
    public ResponseFormat<MemberReadDto> updateMember(@PathVariable String memberNickname,
                             @RequestBody @Valid MemberUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.updateMember(memberNickname, updateDto));
    }

    @DeleteMapping("/member/{memberNickname}")
    public ResponseFormat<Void> deleteMember(@PathVariable String memberNickname) {
        memberService.deleteMember(memberNickname);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/refresh")
    public ResponseFormat<TokenDto> refresh(@RequestBody @Valid TokenDto tokenDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.refreshAccessToken(tokenDto));
    }


    /** 회원가입 시, 중복 확인 버튼 체크 위한 API **/

    @PostMapping("/member/checkEmail/{email}")
    public ResponseFormat<String> checkEmailValid(@PathVariable String email) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkEmailValid(email));
    }

    @PostMapping("/member/checkNickname/{nickname}")
    public ResponseFormat<String> checkNicknameValid(@PathVariable String nickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkNicknameValid(nickname));
    }

    @PostMapping("/member/checkTelephone/{telephone}")
    public ResponseFormat<String> checkTelephoneValid(@PathVariable String telephone) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkTelephoneValid(telephone));
    }
}
