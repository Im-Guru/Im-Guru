package kr.co.imguru.domain.member.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.co.imguru.domain.member.dto.MemberUpdateDto;
import kr.co.imguru.domain.skill.entity.Skill;
import kr.co.imguru.global.common.BaseEntity;
import kr.co.imguru.global.common.Gender;
import kr.co.imguru.global.common.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "nickname")
    @NotBlank
    private String nickname;

    @Column(name = "telephone")
    @NotBlank
    private String telephone;

    @Column(name = "job")
    @NotBlank
    private String job;

    // 다음 주소 API 사용 예정, 해당 속성은 변경될 가능성 있음
//    @Column(name = "address")
//    @NotBlank
//    private String address;

    @Column(name = "zone_code")
    private String zoneCode;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "member_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Builder
    public Member(String email,
                  String password,
                  String name,
                  String nickname,
                  String telephone,
                  String job,
                  String zoneCode,
                  String roadAddress,
                  String detailAddress,
                  LocalDate birthDate,
                  Gender gender,
                  Role role,
                  Skill skill) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.telephone = telephone;
        this.job = job;
        this.zoneCode = zoneCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.skill = skill;
    }

    public void changeMember(MemberUpdateDto updateDto, Skill updateSkill) {
        this.password = updateDto.getPassword();
        this.name = updateDto.getName();
        this.job = updateDto.getJob();
        this.zoneCode = updateDto.getZoneCode();
        this.roadAddress = updateDto.getRoadAddress();
        this.detailAddress = updateDto.getDetailAddress();
        this.birthDate = updateDto.getBirthDate();
        this.skill = updateSkill;
    }

}
