package kr.co.imguru.domain.guru.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;
import kr.co.imguru.domain.guru.service.GuruInfoService;
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
public class GuruInfoRestController {

    private final GuruInfoService guruInfoService;

    //Create
    @PostMapping("/guru/write")
    public ResponseFormat<Void> createGuruInfo(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody @Valid GuruInfoCreateDto createDto) {
        guruInfoService.createGuruInfo(userDetails.getUsername(), createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    //Read One
    @GetMapping("/guru/{memberNickname}")
    public ResponseFormat<GuruInfoReadDto> readGuruInfo(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getGuruInfo(memberNickname));
    }

    @PostMapping("/guru/member/{memberNickname}")
    public ResponseFormat<GuruInfoReadDto> readGuruInfoByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getGuruInfoByMemberNickname(memberNickname));
    }

    @PostMapping("/guru/member")
    public ResponseFormat<GuruInfoReadDto> readGuruInfoByMember(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getGuruInfoByMember(userDetails.getUsername()));
    }

    @PostMapping("/guru/myInfo")
    public ResponseFormat<GuruInfoReadDto> readGuruInfoByLoginMember(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getGuruInfoByLoginMember(userDetails.getUsername()));
    }

    //Read All
    @GetMapping("/guru/all")
    public ResponseFormat<List<GuruInfoReadDto>> readAllGuruInfos() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getAllGuruInfos());
    }

    //Update
    @PostMapping("/guru/update")
    public ResponseFormat<GuruInfoReadDto> updateGuruInfo (@AuthenticationPrincipal UserDetails userDetails,
                                                           @RequestBody @Valid GuruInfoUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.updateGuruInfo(userDetails.getUsername(), updateDto));
    }

    //Delete
    @DeleteMapping("/guru/{memberNickname}")
    public ResponseFormat<Void> deleteGuruInfo(@PathVariable String memberNickname) {
        guruInfoService.deleteGuruInfo(memberNickname);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

}
