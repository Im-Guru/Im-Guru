package kr.co.imguru.dummy;

import kr.co.imguru.domain.guru.repository.GuruInfoRepository;
import kr.co.imguru.domain.guru.service.GuruInfoService;
import kr.co.imguru.domain.member.dto.MemberCreateDto;
import kr.co.imguru.domain.member.repository.MemberRepository;
import kr.co.imguru.domain.member.service.MemberService;
import kr.co.imguru.domain.post.repository.PostRepository;
import kr.co.imguru.domain.post.service.PostService;
import kr.co.imguru.domain.reply.repository.ReplyRepository;
import kr.co.imguru.domain.reply.service.ReplyService;
import kr.co.imguru.domain.review.repository.ReviewRepository;
import kr.co.imguru.domain.review.service.ReviewService;
import kr.co.imguru.domain.skill.dto.SkillCreateDto;
import kr.co.imguru.domain.skill.repository.SkillRepository;
import kr.co.imguru.domain.skill.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class DummyDataTest {

    @Autowired
    SkillService skillService;
    @Autowired
    SkillRepository skillRepository;

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GuruInfoService guruInfoService;
    @Autowired
    GuruInfoRepository guruInfoRepository;

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyService replyService;
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void createSkill() {
        SkillCreateDto dto1 = SkillCreateDto.builder()
                .name("이용자")
                .build();

        SkillCreateDto dto2 = SkillCreateDto.builder()
                .name("프로그래머")
                .build();

        SkillCreateDto dto3 = SkillCreateDto.builder()
                .name("냉장고수리기사")
                .build();

        SkillCreateDto dto4 = SkillCreateDto.builder()
                .name("디자이너")
                .build();

        SkillCreateDto dto5 = SkillCreateDto.builder()
                .name("인테리어")
                .build();

        SkillCreateDto dto6 = SkillCreateDto.builder()
                .name("포토샵")
                .build();

        SkillCreateDto dto7 = SkillCreateDto.builder()
                .name("영상기획자")
                .build();

        skillService.createSkill(dto1);
        skillService.createSkill(dto2);
        skillService.createSkill(dto3);
        skillService.createSkill(dto4);
        skillService.createSkill(dto5);
        skillService.createSkill(dto6);
        skillService.createSkill(dto7);

    }

    @Test   // 일반 회원
    void createMember() {
        for (int i = 1; i < 10; i++) {

            MemberCreateDto create = MemberCreateDto.builder()
                    .email("test" + i + "@test.com")
                    .password("password" + i)
                    .confirmPassword("password" + i)
                    .name("이름_" + i)
                    .nickname("일반회원" + i)
                    .telephone("0100000" + i)
                    .job("대학생")
                    .zoneCode("12345")
                    .roadAddress("서울시 아무구" + i)
                    .detailAddress("아무아파트 100동 " + i + "호")
                    .birthDate(LocalDate.of(i % 15 + 1980, i % 11 + 1, i % 20 + 1))
                    .gender("MALE")
                    .build();

            memberService.createMember(create);
        }
    }

    @Test   // 전문가 회원
    void createGuruMember() {

        MemberCreateDto create1 = MemberCreateDto.builder()
                .email("guru1@test.com")
                .password("password1")
                .confirmPassword("password1")
                .name("고수_1")
                .nickname("고수회원1")
                .telephone("01011111")
                .job("고수직업1")
                .zoneCode("12345")
                .roadAddress("서울시 아무구1")
                .detailAddress("아무아파트 100동 101호")
                .birthDate(LocalDate.of(1980, 1, 1))
                .gender("MALE")
                .skillName("프로그래머")
                .build();

        MemberCreateDto create2 = MemberCreateDto.builder()
                .email("guru2@test.com")
                .password("password2")
                .confirmPassword("password2")
                .name("고수_2")
                .nickname("고수회원2")
                .telephone("01011112")
                .job("고수직업2")
                .zoneCode("12345")
                .roadAddress("서울시 아무구2")
                .detailAddress("아무아파트 100동 102호")
                .birthDate(LocalDate.of(1980, 2, 2))
                .gender("FEMALE")
                .skillName("디자이너")
                .build();

        MemberCreateDto create3 = MemberCreateDto.builder()
                .email("guru3@test.com")
                .password("password3")
                .confirmPassword("password3")
                .name("고수_3")
                .nickname("고수회원3")
                .telephone("01011113")
                .job("고수직업3")
                .zoneCode("12345")
                .roadAddress("서울시 아무구3")
                .detailAddress("아무아파트 100동 103호")
                .birthDate(LocalDate.of(1980, 3, 3))
                .gender("MALE")
                .skillName("인테리어")
                .build();

        memberService.createMember(create1);
        memberService.createMember(create2);
        memberService.createMember(create3);

    }

}
