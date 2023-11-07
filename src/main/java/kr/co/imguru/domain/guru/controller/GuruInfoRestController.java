package kr.co.imguru.domain.guru.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;
import kr.co.imguru.domain.guru.service.GuruInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GuruInfoRestController {

    private final GuruInfoService guruInfoService;

    //create
    @PostMapping("/guru/{memberNickname}")
    public Long createGuruInfo(@PathVariable String memberNickname,
                               @RequestBody @Valid GuruInfoCreateDto createDto) {
        return guruInfoService.createGuruInfo(memberNickname, createDto);
    }

    //read one
    @GetMapping("/guru/{memberNickname}")
    public GuruInfoReadDto readGuruInfo(@PathVariable String memberNickname) {
        return guruInfoService.getGuruInfo(memberNickname);
    }

    //read all
    @GetMapping("/guru/all")
    public List<GuruInfoReadDto> readAllGuruInfos() {
        return guruInfoService.getAllGuruInfos();
    }

    //update
    @PutMapping("/guru/{memberNickname}")
    public Long updateGuruInfo(@PathVariable String memberNickname,
                               @RequestBody @Valid GuruInfoUpdateDto updateDto) {
        return guruInfoService.updateGuruInfo(memberNickname, updateDto);
    }

    //delete
    @DeleteMapping("/guru/{memberNickname}")
    void deleteGuruInfo(@PathVariable String memberNickname) {
        guruInfoService.deleteGuruInfo(memberNickname);
    }


}
