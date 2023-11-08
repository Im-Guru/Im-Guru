package kr.co.imguru.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportReplyCreateDto {

    private String memberNickname;

    private Long replyId;

    private String categoryName;

    private String description;

}
