package kr.co.imguru.domain.member.service;

import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.global.auth.TokenDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    void createMember(MemberCreateDto createDto);

    void createGuruMember(MemberCreateDto createDto);

    MemberReadDto loginMember(MemberLoginDto loginDto);

    MemberReadDto getMember(String memberNickname);

    List<MemberReadDto> getAllMembers();

    List<MemberReadDto> getAllUserMembers();

    List<MemberReadDto> getAllGuruMembers();

    MemberReadDto updateMember(String email, MemberUpdateDto updateDto);

    void deleteMember(String memberNickname);

    TokenDto refreshAccessToken(TokenDto tokenDto);

    boolean checkAuthor(String email, Long postId);

    boolean checkReplier(String email, Long replyId);

    MemberReadDto getMemberByLoginMember(String email);

    MemberReadDto getMemberDetailByMemberNickname(String memberNickname);

    Long uploadMemberImage(String email, List<MultipartFile> files) throws IOException;



    String checkEmailValid(String email);

    String checkNicknameValid(String nickname);

    String checkTelephoneValid(String telephone);

}
