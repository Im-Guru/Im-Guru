package kr.co.imguru.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    private String memberNickname;

    private String categoryName;

    private String title;

    private String content;

    private String price;

}
