package kr.co.imguru.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCreateDto {

    private String memberNickname;

    private Long postId;

    private String content;

}
