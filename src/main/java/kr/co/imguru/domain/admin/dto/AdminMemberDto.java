package kr.co.imguru.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMemberDto {

    private Long memberId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String telephone;

    private String job;

    private String zoneCode;

    private String roadAddress;

    private String detailAddress;

    private LocalDate birthDate;

    private String gender;

    private String role;

    private String skillName;

    private boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
