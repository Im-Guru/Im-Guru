package kr.co.imguru.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateDto {

    private String userNickname;

    private String guruNickname;

    private String content;

    private int rate;

}
