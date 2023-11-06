package kr.co.imguru.domain.member.dto;

import kr.co.imguru.domain.skill.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDto {

    private String password;

    private String confirmPassword;

    private String name;

    private String job;

    private String address;

    private LocalDate birthDate;

    private String gender;

    private String skillName;

}
