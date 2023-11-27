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
public class AdminGuruInfoDto {

    private Long guruInfoId;

    private String memberNickname;

    private String intro;

    private String companyName;

    private String position;

    private String careerAt;

    private String contactTime;

    private String workArea;

    private String description;

    private boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
