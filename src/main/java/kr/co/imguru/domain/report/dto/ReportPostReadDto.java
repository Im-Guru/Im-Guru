package kr.co.imguru.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportPostReadDto {

    private Long reportPostId;

    private String memberNickname;

    private Long postId;

    private String postTitle;

    private String postContent;

    private String postWriter;

    private String categoryName;

    private String description;
}
