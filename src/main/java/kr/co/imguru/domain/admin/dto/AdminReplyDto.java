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
public class AdminReplyDto {

    private Long replyId;

    private String memberNickname;

    private Long postId;

    private String postTitle;

    private String content;

    private Long likeCnt;

    private String memberSkill;

    private boolean isDelete;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
