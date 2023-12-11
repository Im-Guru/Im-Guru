package kr.co.imguru.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.service.MemberService;
import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.auth.TokenDto;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
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

    @PostMapping("/member/update")
    public ResponseFormat<MemberReadDto> updateMember(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody @Valid MemberUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.updateMember(userDetails.getUsername(), updateDto));
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

    @PostMapping("/member/checkAuthor/{postId}")
    public ResponseFormat<Boolean> checkAuthor(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkAuthor(userDetails.getUsername(), postId));
    }

    @PostMapping("/member/checkReplier/{replyId}")
    public ResponseFormat<Boolean> checkReplier(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkReplier(userDetails.getUsername(), replyId));
    }

    @PostMapping("/member/myInfo")
    public ResponseFormat<MemberReadDto> getMemberByLoginMember(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberByLoginMember(userDetails.getUsername()));
    }

    @PostMapping("/member/guruInfo/{payId}")
    public ResponseFormat<MemberReadDto> getGuruByPay(@PathVariable Long payId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getGuruByPay(payId));
    }

    @PostMapping("/member/{memberNickname}")
    public ResponseFormat<MemberReadDto> getMemberDetailByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetailByMemberNickname(memberNickname));
    }

    @PostMapping(value = "/member/image", consumes = {"multipart/form-data"})
    public ResponseFormat<Long> uploadMemberImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {

        Long memberId = memberService.uploadMemberImage(userDetails.getUsername(), files);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberId);
    }

    /*파일 링크 클릭 시 파일 저장*/
    @GetMapping("/member/files/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName,
                                          HttpServletRequest request) throws IOException {
        /*프로젝트 루트 경로*/
        String rootDir = System.getProperty("user.dir");

        /*file의 path를 저장 -> 클릭 시 파일로 이동*/
        Path filePath = Path.of(rootDir + "/media/member/" + fileName);

        /*파일의 패스를 uri로 변경하고 resource로 저장.*/
        Resource resource = new UrlResource(filePath.toUri());

        /*컨텐츠 타입을 가지고 온다.*/
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

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
