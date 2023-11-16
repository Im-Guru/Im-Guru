package kr.co.imguru.domain.member.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.skill.entity.Skill;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import kr.co.imguru.global.auth.JwtProvider;
import kr.co.imguru.global.common.Gender;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SkillRepository skillRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void createMember(MemberCreateDto createDto) {

        isEmail(createDto.getEmail());
        isTelephone(createDto.getTelephone());
        isNickname(createDto.getNickname());
        isConfirmPassword(createDto.getPassword(), createDto.getConfirmPassword());

        memberRepository.save(toEntity(createDto));
    }

    @Override
    @Transactional
    public void createGuruMember(MemberCreateDto createDto) {

        isEmail(createDto.getEmail());
        isTelephone(createDto.getTelephone());
        isNickname(createDto.getNickname());
        isConfirmPassword(createDto.getPassword(), createDto.getConfirmPassword());


        Optional<Skill> skill = skillRepository.findByNameAndIsDeleteFalse(createDto.getSkillName());
        isSkill(skill);

        memberRepository.save(toGuru(createDto, skill.get()));
    }

    @Override
    @Transactional
    public MemberReadDto loginMember(MemberLoginDto loginDto) {
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(loginDto.getEmail());

        isMember(member);

        isPassword(loginDto.getPassword(), member.get().getPassword());

        return toReadDtoWithToken(member.get());
    }

    @Override
    @Transactional
    public MemberReadDto getMember(String memberNickname) {
        final Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        return toReadDto(member.get());
    }

    @Override
    @Transactional
    public List<MemberReadDto> getAllMembers() {
        return memberRepository.findAllByIsDeleteFalse().stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<MemberReadDto> getAllUserMembers() {
        return memberRepository.findAllByRoleAndIsDeleteFalse(Role.valueOf("ROLE_USER")).stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public List<MemberReadDto> getAllGuruMembers() {
        return memberRepository.findAllByRoleAndIsDeleteFalse(Role.valueOf("ROLE_GURU")).stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public MemberReadDto updateMember(String memberNickname, MemberUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
        isMember(member);
        isConfirmPassword(updateDto.getPassword(), updateDto.getConfirmPassword());
        isPassword(member.get().getPassword(), updateDto.getPassword());

        member.get().changeMember(updateDto, skillRepository.findByNameAndIsDeleteFalse(updateDto.getSkillName()).get());

        memberRepository.save(member.get());

        return toReadDto(member.get());
    }

    @Override
    @Transactional
    public void deleteMember(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        member.get().changeDeleteAt();

        memberRepository.save(member.get());
    }

    @Override
    public String checkEmailValid(String email) {
        isEmail(email);

        return email;
    }

    @Override
    public String checkNicknameValid(String nickname) {
        isNickname(nickname);

        return nickname;
    }

    @Override
    public String checkTelephoneValid(String telephone) {
        isTelephone(telephone);

        return telephone;
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
            throw new IllegalArgumentException(ResponseStatus.FAIL_MEMBER_GENDER_NOT_FOUND);
        }
    }

    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NOT_FOUND);
        }
    }

    private void isConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_MEMBER_CONFIRM_PASSWORD);
        }
    }

    private void isPassword(String requestPassword, String getPassword) {
        if (!passwordEncoder.matches(requestPassword, getPassword)) {
            throw new IllegalArgumentException(ResponseStatus.FAIL_MEMBER_PASSWORD_NOT_MATCHED);
        }
    }

    private Member toEntity(MemberCreateDto dto) {
        Gender gender = isGender(dto.getGender());

        return Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .telephone(dto.getTelephone())
                .zoneCode(dto.getZoneCode())
                .roadAddress(dto.getRoadAddress())
                .detailAddress(dto.getDetailAddress())
                .job(dto.getJob())
                .birthDate(dto.getBirthDate())
                .gender(gender)
                .role(Role.ROLE_USER)
                .skill(skillRepository.findByNameAndIsDeleteFalse("이용자").get())
                .build();
    }

    private Member toGuru(MemberCreateDto dto, Skill skill) {
        Gender gender = isGender(dto.getGender());

        return Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .telephone(dto.getTelephone())
                .zoneCode(dto.getZoneCode())
                .roadAddress(dto.getRoadAddress())
                .detailAddress(dto.getDetailAddress())
                .job(dto.getJob())
                .birthDate(dto.getBirthDate())
                .gender(gender)
                .role(Role.ROLE_GURU)
                .skill(skill)
                .build();
    }

    private MemberReadDto toReadDto(Member member) {
        return MemberReadDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .zoneCode(member.getZoneCode())
                .roadAddress(member.getRoadAddress())
                .detailAddress(member.getDetailAddress())
                .job(member.getJob())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(member.getSkill().getName())
                .build();
    }

    private MemberReadDto toReadDtoWithToken(Member member) {
        return MemberReadDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .zoneCode(member.getZoneCode())
                .roadAddress(member.getRoadAddress())
                .detailAddress(member.getDetailAddress())
                .job(member.getJob())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getRole()))
                .skillName(member.getSkill().getName())
                .token(jwtProvider.createToken(member.getEmail(), String.valueOf(member.getRole())))
                .build();
    }

}
