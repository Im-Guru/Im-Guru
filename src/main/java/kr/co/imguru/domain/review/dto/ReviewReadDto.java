package kr.co.imguru.domain.review.dto;

import kr.co.imguru.domain.file.entity.FileFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReadDto {

    private Long reviewId;

    private String userNickname;

    private String guruNickname;

    private String guruSkill;

    private Long payId;

    private String content;

    private List<FileFormat> fileFormat;

    private int rate;

    private Long likeCnt;

    private LocalDateTime regDate;

}
