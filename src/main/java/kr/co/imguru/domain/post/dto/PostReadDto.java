package kr.co.imguru.domain.post.dto;

import kr.co.imguru.domain.file.entity.File;
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
public class PostReadDto {

    private Long postId;

    private String memberNickname;

    private FileFormat memberImage;

    private String postCategory;

    private String title;

    private String content;

    private List<File> fileList;

    private boolean isGuru;

    private String skillName;

    private String price;

    private int replyCnt;

    private Long likeCnt;

    private Long viewCnt;

    private LocalDateTime regDate;
}
