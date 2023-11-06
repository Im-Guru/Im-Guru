package kr.co.imguru.domain.member.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Column(name = "email", unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "nickname", unique = true)
    @NotBlank
    private String nickname;

    @Column(name = "telephone", unique = true)
    @NotBlank
    private String telephone;

    @Column(name = "job")
    @NotBlank
    private String job;

    // 다음 주소 API 사용 예정, 해당 속성은 변경될 가능성 있음
    @Column(name = "address")
    @NotBlank
    private String address;

    @Column(name = "birth_date")
    @NotBlank
    private LocalDate birthDate;

    @Column(name = "member_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email,
                  String password,
                  String name,
                  String nickname,
                  String telephone,
                  String job,
                  String address,
                  LocalDate birthDate,
                  Gender gender,
                  Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.telephone = telephone;
        this.job = job;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
    }


}
