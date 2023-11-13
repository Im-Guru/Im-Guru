package kr.co.imguru.domain.member.service;

import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;

import java.util.List;

public interface MemberService {

    void createMember(MemberCreateDto createDto);

    void createGuruMember(MemberCreateDto createDto);

    MemberReadDto loginMember(MemberLoginDto loginDto);

    MemberReadDto getMember(String memberNickname);

    List<MemberReadDto> getAllMembers();

    List<MemberReadDto> getAllUserMembers();

    List<MemberReadDto> getAllGuruMembers();

    MemberReadDto updateMember(String memberNickname, MemberUpdateDto updateDto);

    void deleteMember(String memberNickname);

}
