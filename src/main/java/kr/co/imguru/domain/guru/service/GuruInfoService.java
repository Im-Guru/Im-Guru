package kr.co.imguru.domain.guru.service;


import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;

import java.util.List;

public interface GuruInfoService {

    void createGuruInfo(String email, GuruInfoCreateDto createDto);

    GuruInfoReadDto getGuruInfo(String memberNickname);

    GuruInfoReadDto getGuruInfoByMemberNickname(String memberNickname);

    GuruInfoReadDto getGuruInfoByMember(String email);

    GuruInfoReadDto getGuruInfoByLoginMember(String email);

    List<GuruInfoReadDto> getAllGuruInfos();

    GuruInfoReadDto updateGuruInfo(String email, GuruInfoUpdateDto updateDto);

    void deleteGuruInfo(String memberNickname);
}
