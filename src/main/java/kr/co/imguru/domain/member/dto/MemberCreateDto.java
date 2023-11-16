package kr.co.imguru.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {

    private String email;

    private String password;

    private String confirmPassword;

    private String name;

    private String nickname;

    private String telephone;

    private String job;

    private String zoneCode;

    private String roadAddress;

    private String detailAddress;

    private LocalDate birthDate;

    private String gender;

    private String skillName;

}
