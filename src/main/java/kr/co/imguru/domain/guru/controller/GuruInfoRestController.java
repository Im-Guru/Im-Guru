package kr.co.imguru.domain.guru.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;
import kr.co.imguru.domain.guru.service.GuruInfoService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GuruInfoRestController {

    private final GuruInfoService guruInfoService;

    //Create
    @PostMapping("/guru/{memberNickname}")
    public ResponseFormat<Void> createGuruInfo(@PathVariable String memberNickname,
                                               @RequestBody @Valid GuruInfoCreateDto createDto) {
        guruInfoService.createGuruInfo(memberNickname, createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    //Read One
    @GetMapping("/guru/{memberNickname}")
    public ResponseFormat<GuruInfoReadDto> readGuruInfo(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getGuruInfo(memberNickname));
    }

    //Read All
    @GetMapping("/guru/all")
    public ResponseFormat<List<GuruInfoReadDto>> readAllGuruInfos() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.getAllGuruInfos());
    }

    //Update
    @PutMapping("/guru/{memberNickname}")
    public ResponseFormat<GuruInfoReadDto> updateGuruInfo (@PathVariable String memberNickname,
                               @RequestBody @Valid GuruInfoUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, guruInfoService.updateGuruInfo(memberNickname, updateDto));
    }

    //Delete
    @DeleteMapping("/guru/{memberNickname}")
    public ResponseFormat<Void> deleteGuruInfo(@PathVariable String memberNickname) {
        guruInfoService.deleteGuruInfo(memberNickname);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }


}
