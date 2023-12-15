package kr.co.imguru.domain.member.dto;

import kr.co.imguru.domain.file.entity.File;
import kr.co.imguru.domain.file.entity.FileFormat;
import kr.co.imguru.global.auth.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    private String zoneCode;

    private String roadAddress;

    private String detailAddress;

    private LocalDate birthDate;

    private String gender;

    private String role;

    private String skillName;

    private TokenDto token;

    private File file;

}
