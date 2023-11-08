package kr.co.imguru.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReadDto {

    private String userNickname;

    private String guruNickname;

    private String guruSkill;

    private String content;

    private int rate;

    private Long likeCnt;

}
