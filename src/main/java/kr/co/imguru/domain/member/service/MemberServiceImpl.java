package kr.co.imguru.domain.member.service;

import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import kr.co.imguru.global.common.Gender;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SkillRepository skillRepository;

    @Override
    public void createMember(MemberCreateDto createDto) {

        isEmail(createDto.getEmail());
        isTelephone(createDto.getTelephone());
        isNickname(createDto.getNickname());

        memberRepository.save(toEntity(createDto));
    }

    @Override
    public void createGuruMember(MemberCreateDto createDto) {

        isEmail(createDto.getEmail());
        isTelephone(createDto.getTelephone());
        isNickname(createDto.getNickname());

        memberRepository.save(toGuru(createDto));
    }

    @Override
    public MemberReadDto getMember(String memberNickname) {
        final Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    public List<MemberReadDto> getAllMembers() {
        return memberRepository.findAllByIsDeleteFalse().stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    public MemberReadDto updateMember(String memberNickname, MemberUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        member.get().changeMember(updateDto, skillRepository.findByNameAndIsDeleteFalse(updateDto.getSkillName()).get());

        memberRepository.save(member.get());

        return toReadDto(member.get());
    }

    @Override
    public void deleteMember(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        member.get().changeDeleteAt();

        memberRepository.save(member.get());
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isTelephone(String telephone) {
        if (memberRepository.existsByTelephoneAndIsDeleteFalse(telephone)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isNickname(String nickname) {
        if (memberRepository.existsByNicknameAndIsDeleteFalse(nickname)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_NICKNAME_DUPLICATED);
        }
    }

    private void isEmail(String email) {
        if (memberRepository.existsByEmailAndIsDeleteFalse(email)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_EMAIL_DUPLICATED);
        }
    }

    private Gender isGender(String genderName) {
        try {
            return Gender.valueOf(genderName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_ILLEGAL_ACCESS);
        }
    }

//    private void isPassword(String requestPassword, String getPassword) {
//        if (!passwordEncoder.matches(requestPassword, getPassword)) {
////            throw new WrongPasswordException(ResponseStatus.FAIL_MEMBER_PASSWORD_NOT_MATCHED);
//            throw new RuntimeException();
//
//        }
//    }

    private Member toEntity(MemberCreateDto dto) {
        Gender gender = isGender(dto.getGender());

        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .telephone(dto.getTelephone())
                .address(dto.getAddress())
                .job(dto.getJob())
                .birthDate(dto.getBirthDate())
                .gender(gender)
                .role(Role.ROLE_USER)
                .skill(skillRepository.findByNameAndIsDeleteFalse("이용자").get())
                .build();
    }

    private Member toGuru(MemberCreateDto dto) {
        Gender gender = isGender(dto.getGender());

        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .telephone(dto.getTelephone())
                .address(dto.getAddress())
                .job(dto.getJob())
                .birthDate(dto.getBirthDate())
                .gender(gender)
                .role(Role.ROLE_GURU)
                .skill(skillRepository.findByNameAndIsDeleteFalse(dto.getSkillName()).get())
                .build();
    }

    private MemberReadDto toReadDto(Member member) {
        return MemberReadDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .address(member.getAddress())
                .job(member.getJob())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(member.getSkill().getName())
                .build();
    }

}
