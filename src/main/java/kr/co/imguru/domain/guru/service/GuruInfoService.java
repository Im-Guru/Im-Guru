package kr.co.imguru.domain.guru.service;


import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;

import java.util.List;

public interface GuruInfoService {

    void createGuruInfo(String memberNickname, GuruInfoCreateDto createDto);

    GuruInfoReadDto getGuruInfo(String memberNickname);

    List<GuruInfoReadDto> getAllGuruInfos();

    GuruInfoReadDto updateGuruInfo(String memberNickname, GuruInfoUpdateDto updateDto);

    void deleteGuruInfo(String memberNickname);
}
