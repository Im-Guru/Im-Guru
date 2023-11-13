package kr.co.imguru.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReadDto {

    private Long memberId;

    private String email;

    private String name;

    private String nickname;

    private String telephone;

    private String job;

    private String address;

    private LocalDate birthDate;

    private String gender;

    private String role;

    private String skillName;

    private String token;

}
