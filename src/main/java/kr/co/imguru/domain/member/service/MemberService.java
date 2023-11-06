package kr.co.imguru.domain.member.service;

import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;

import java.util.List;

public interface MemberService {

    Long createMember(MemberCreateDto createDto);

    Long createGuruMember(MemberCreateDto createDto);

    MemberReadDto getMember(String memberNickname);

    List<MemberReadDto> getAllMembers();

    Long updateMember(String memberNickname, MemberUpdateDto updateDto);

    void deleteMember(String memberNickname);

}
