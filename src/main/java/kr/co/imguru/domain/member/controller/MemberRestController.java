package kr.co.imguru.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.service.MemberService;
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
    public Long createMember(@RequestBody @Valid MemberCreateDto createDto) {
        return memberService.createMember(createDto);
    }

    @PostMapping("/member/guru")
    public Long createGuruMember(@RequestBody @Valid MemberCreateDto createDto) {
        return memberService.createGuruMember(createDto);
    }

    // Read One
    @GetMapping("/member/{memberNickname}")
    public MemberReadDto readMember(@PathVariable String memberNickname) {
        return memberService.getMember(memberNickname);
    }

    // Read All
    @GetMapping("/member/all")
    public List<MemberReadDto> readAllMembers() {
        return memberService.getAllMembers();
    }

    // Update
    @PutMapping("/member/{memberNickname}")
    public Long updateMember(@PathVariable String memberNickname,
                             @RequestBody @Valid MemberUpdateDto updateDto) {
        return memberService.updateMember(memberNickname, updateDto);
    }

    // Delete
    @DeleteMapping("/member/{memberNickname}")
    public void deleteMember(@PathVariable String memberNickname) {
        memberService.deleteMember(memberNickname);
    }

}
