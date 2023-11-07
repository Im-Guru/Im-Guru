package kr.co.imguru.domain.guru.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuruInfoUpdateDto {

    private String intro;

    private String companyName;

    private String position;

    private String careerAt;

    private String contactTime;

    private String workArea;

    private String description;
}
