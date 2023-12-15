package kr.co.imguru.domain.member.service;

import jakarta.transaction.Transactional;
import kr.co.imguru.domain.file.entity.File;
import kr.co.imguru.domain.file.entity.FileFormat;
import kr.co.imguru.domain.file.repository.FileRepository;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.dto.MemberLoginDto;
import kr.co.imguru.domain.member.dto.MemberReadDto;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.member.entity.Member;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.pay.entity.Pay;
import kr.co.imguru.domain.pay.repository.PayRepository;
import kr.co.imguru.domain.post.entity.Post;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.reply.entity.Reply;
import kr.co.imguru.domain.reply.repository.ReplyRepository;
import kr.co.imguru.domain.review.dto.ReviewReadDto;
import kr.co.imguru.domain.skill.entity.Skill;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import kr.co.imguru.global.auth.JwtProvider;
import kr.co.imguru.global.auth.Token;
import kr.co.imguru.global.auth.TokenDto;
import kr.co.imguru.global.auth.TokenRepository;
import kr.co.imguru.global.common.Gender;
import kr.co.imguru.global.common.Role;
import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.InvalidRequestException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SkillRepository skillRepository;

    private final PostRepository postRepository;

    private final ReplyRepository replyRepository;

    private final PayRepository payRepository;

    private final TokenRepository tokenRepository;

    private final FileRepository fileRepository;

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

        /*File*/
        Optional<File> file = fileRepository.findOneFileByFileKey("member", member.get().getId());
        if (file.isEmpty()) {
            return toReadDtoWithFile(member.get(), null);
        } else {
            return toReadDtoWithFile(member.get(), file.get());
        }
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
    public MemberReadDto updateMember(String email, MemberUpdateDto updateDto) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);
        isMember(loginMember);

        isConfirmPassword(updateDto.getPassword(), updateDto.getConfirmPassword());

//        isPassword(loginMember.get().getPassword(), updateDto.getPassword());

        loginMember.get().changeMember(updateDto, passwordEncoder.encode(updateDto.getPassword()), skillRepository.findByNameAndIsDeleteFalse(updateDto.getSkillName()).get());

        memberRepository.save(loginMember.get());

        return toReadDto(loginMember.get());
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

    public String createRefreshToken(Member member) {
//        Token token = tokenRepository.save(
//                Token.builder()
//                        .id(member.getId())
//                        .refreshToken(UUID.randomUUID().toString())
//                        .expiration(3600)        // 유효시간 설정 - 60분
//                        .build()
//        );
//
//        return token.getRefreshToken();
        try {
            Token token = tokenRepository.save(
                    Token.builder()
                            .id(member.getId())
                            .refreshToken(UUID.randomUUID().toString())
                            .expiration(3600)        // 유효시간 설정 - 60분
                            .build()
            );

            return token.getRefreshToken();
        } catch (DataAccessException e) {
            // Redis에 연결할 수 없는 경우
            // 예외 처리 코드 추가 (예: 로그 출력 등)
            e.printStackTrace();
            return null; // 또는 다른 적절한 처리
        }
    }

    public Token validRefreshToken(Member member, String refreshToken) {
        Optional<Token> token = tokenRepository.findById(member.getId());

        isToken(token);

        // redis에 해당 유저의 토큰이 존재하는지 체크
        if (token.get().getRefreshToken() == null) {
            return null;
        } else {
            // refreshToken은 있지만, 만료시간이 얼마 남지 않았다면 만료시간 연장
            if (token.get().getExpiration() < 10) {
                token.get().setExpiration(3600);
                tokenRepository.save(token.get());
            }

            // token이 같은지 비교
            if (!token.get().getRefreshToken().equals(refreshToken)) {
                throw new InvalidRequestException(ResponseStatus.FAIL_REFRESHTOKEN_NOT_FOUND);
            } else {
                return token.get();
            }
        }
    }

    @Override
    public TokenDto refreshAccessToken(TokenDto tokenDto) {
        String email = jwtProvider.getEmail(tokenDto.getAccessToken());

        Optional<Member> member = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(member);

        Token refreshToken = validRefreshToken(member.get(), tokenDto.getRefreshToken());

        isRefreshToken(refreshToken);

        return TokenDto.builder()
                .accessToken(jwtProvider.createToken(email, String.valueOf(member.get().getRole())))
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }

    @Override
    @Transactional
    public boolean checkAuthor(String email, Long postId) {
        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(postId);

        if (post.get().getMember().getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean checkReplier(String email, Long replyId) {
        Optional<Reply> reply = replyRepository.findByIdAndIsDeleteFalse(replyId);
        isReply(reply);

        if (reply.get().getMember().getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public MemberReadDto getMemberByLoginMember(String email) {
        Optional<Member> loginMember = memberRepository.findByEmailAndIsDeleteFalse(email);

        isMember(loginMember);

        /*File*/
        Optional<File> file = fileRepository.findOneFileByFileKey("member", loginMember.get().getId());
        if (file.isEmpty()) {
            return toReadDtoWithFile(loginMember.get(), null);
        } else {
            return toReadDtoWithFile(loginMember.get(), file.get());
        }

    }

    @Override
    @Transactional
    public MemberReadDto getGuruByPay(Long payId) {
        Optional<Pay> pay = payRepository.findById(payId);
        isPay(pay);

        Optional<Post> post = postRepository.findByIdAndIsDeleteFalse(Long.valueOf(pay.get().getMercntParam1()));

        Optional<Member> guru = memberRepository.findByIdAndIsDeleteFalse(post.get().getMember().getId());
        isMember(guru);

        return toReadDto(guru.get());
    }

    @Override
    @Transactional
    public MemberReadDto getMemberDetailByMemberNickname(String memberNickname) {
        Optional<Member> member = memberRepository.findByNicknameAndIsDeleteFalse(memberNickname);

        isMember(member);

        /*File*/
        Optional<File> file = fileRepository.findOneFileByFileKey("member", member.get().getId());

        if (file.isEmpty()) {
            return toReadDtoWithFile(member.get(), null);
        } else {
            return toReadDtoWithFile(member.get(), file.get());
        }
    }

    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPay(Optional<Pay> pay) {
        if (pay.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_PAY_NOT_FOUND);
        }
    }

    private void isTelephone(String telephone) {
        if (memberRepository.existsByTelephoneAndIsDeleteFalse(telephone)) {
            throw new DuplicatedException(ResponseStatus.FAIL_MEMBER_TELEPHONE_DUPLICATED);
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

    private void isToken(Optional<Token> token) {
        if (token.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_TOKEN_NOT_FOUND);
        }
    }

    private void isRefreshToken(Token refreshToken) {
        if (refreshToken == null) {
            throw new InvalidRequestException(ResponseStatus.FAIL_LOGIN_NOT_SUCCESS);
        }
    }

    private void isReply(Optional<Reply> reply) {
        if(reply.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPLY_NOT_FOUND);
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

    private MemberReadDto toReadDtoWithFile(Member member, File file) {
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
                .file(file)
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
                .token(TokenDto.builder()
                        .accessToken(jwtProvider.createToken(member.getEmail(), String.valueOf(member.getRole())))
                        .refreshToken(createRefreshToken(member))
                        .build()
                )
                .build();
    }

}
