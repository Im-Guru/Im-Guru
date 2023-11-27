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
public class AdminReportPostDto {

    private Long reportPostId;

    private String memberNickname;

    private Long postId;

    private String postTitle;

    private String postContent;

    private String postWriter;

    private String categoryName;

    private String description;

    private boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
