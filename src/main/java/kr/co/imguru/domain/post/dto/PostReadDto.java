package kr.co.imguru.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReadDto {

    private Long postId;

    private String memberNickname;

    private String postCategory;

    private String title;

    private String content;

    private boolean isGuru;

    private String price;

    private Long likeCnt;

    private Long viewCnt;

}
