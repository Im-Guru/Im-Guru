package kr.co.imguru.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportReplyDto {

    private Long reportReplyId;

    private String memberNickname;

    private Long replyId;

    private String replyContent;

    private String replyWriter;

    private String categoryName;

    private String description;

    private boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
