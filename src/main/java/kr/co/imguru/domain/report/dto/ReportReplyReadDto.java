package kr.co.imguru.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportReplyReadDto {

    private Long reportReplyId;

    private String memberNickname;

    private Long replyId;

    private String replyContent;

    private String replyWriter;

    private String categoryName;

    private String description;
}
