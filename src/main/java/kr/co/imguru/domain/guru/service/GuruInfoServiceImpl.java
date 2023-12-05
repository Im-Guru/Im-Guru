package kr.co.imguru.domain.guru.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.guru.dto.GuruInfoCreateDto;
import kr.co.imguru.domain.guru.dto.GuruInfoReadDto;
import kr.co.imguru.domain.guru.dto.GuruInfoUpdateDto;
import kr.co.imguru.domain.guru.entity.GuruInfo;
import kr.co.imguru.domain.guru.repository.GuruInfoRepository;
import kr.co.imguru.domain.guru.repository.GuruInfoSearchRepository;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuruInfoServiceImpl implements GuruInfoService {

    private final GuruInfoRepository guruRepository;

    private final MemberRepository memberRepository;

    private final GuruInfoSearchRepository guruInfoSearchRepository;

    @Override
    @Transactional
    public void createGuruInfo(String email, GuruInfoCreateDto createDto) {
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);

        isGuruMember(member);

        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByEmail(email);

        isGuruInfoDuplicated(guruInfo);

        GuruInfo guruinfo = toEntity(createDto, member.get());

        guruRepository.save(guruinfo);
    }

    @Override
    @Transactional
    public GuruInfoReadDto getGuruInfo(String memberNickname) {
        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByMemberNickname(memberNickname);

        isGuruInfo(guruInfo);

        return toReadDto(guruInfo.get());
    }

    @Override
    @Transactional
    public GuruInfoReadDto getGuruInfoByMemberNickname(String memberNickname) {
        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByMemberNickname(memberNickname);

        isGuruInfo(guruInfo);

        return toReadDto(guruInfo.get());
    }

    @Override
    @Transactional
    public GuruInfoReadDto getGuruInfoByMember(String email) {
        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByEmail(email);

        isGuruInfo(guruInfo);

        return toReadDto(guruInfo.get());
    }


    @Override
    @Transactional
    public GuruInfoReadDto getGuruInfoByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isGuruMember(loginMember);

        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByEmail(email);
        isGuruInfo(guruInfo);

        return toReadDto(guruInfo.get());
    }

    @Override
    @Transactional
    public List<GuruInfoReadDto> getAllGuruInfos() {
        return guruRepository.findAllByIsDeleteFalse()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    @Override
    @Transactional
    public GuruInfoReadDto updateGuruInfo(String email, GuruInfoUpdateDto updateDto) {
        // 해당 회원이 존재하는지 + 전문가인지 확인
        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);
        isGuruMember(member);

        // 해당 회원이 작성한 전문가 정보가 있는지 확인
        Optional <GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByEmail(email);
        isGuruInfo(guruInfo);

        guruInfo.get().changeGuruInfo(updateDto);

        guruRepository.save(guruInfo.get());

        return toReadDto(guruInfo.get());
    }

    @Override
    @Transactional
    public void deleteGuruInfo(String memberNickname) {
        // 해당 회원이 존재하는지 + 전문가인지 확인
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);
        isGuruMember(member);

        // 해당 회원이 작성한 전문가 정보가 있는지 확인
        Optional<GuruInfo> guruInfo = guruInfoSearchRepository.findGuruInfoByMemberNickname(memberNickname);
        isGuruInfo(guruInfo);

//        guruInfo.get().changeDeleteAt();
        guruRepository.delete(guruInfo.get());

        guruRepository.save(guruInfo.get());
    }

    private void isGuruMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        } else {
            if (member.get().getRole() != Role.ROLE_GURU) {
                throw new NotFoundException(ResponseStatus.FAIL_MEMBER_ROLE_NOT_FOUND);
            }
        }
    }

    private void isGuruInfo(Optional<GuruInfo> guruInfo) {
        if (guruInfo.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_GURU_INFO_NOT_FOUND);
        }
    }

    private void isGuruInfoDuplicated(Optional<GuruInfo> guruInfo) {
        if (guruInfo.isPresent()) {
            throw new DuplicatedException(ResponseStatus.FAIL_GURU_INFO_DUPLICATED);
        }
    }

    private GuruInfo toEntity(GuruInfoCreateDto dto, Member member) {
        return GuruInfo.builder()
                .member(member)
                .intro(dto.getIntro())
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .careerAt(dto.getCareerAt())
                .contactTime(dto.getContactTime())
                .workArea(dto.getWorkArea())
                .description(dto.getDescription())
                .build();
    }

    private GuruInfoReadDto toReadDto(GuruInfo guruInfo) {
        return GuruInfoReadDto.builder()
                .guruInfoId(guruInfo.getId())
                .memberNickname(guruInfo.getMember().getNickname())
                .intro(guruInfo.getIntro())
                .companyName(guruInfo.getCompanyName())
                .position(guruInfo.getPosition())
                .careerAt(guruInfo.getCareerAt())
                .contactTime(guruInfo.getContactTime())
                .workArea(guruInfo.getWorkArea())
                .description(guruInfo.getDescription())
                .build();
    }

}
