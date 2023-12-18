package kr.co.imguru.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostCategory {

    NOTICE("공지사항"),

    QNA("질문있어요"),

    INFO("정보나눠요"),

    HELP("도와주세요"),

    FINDGURU("도사찾아요"),

    FINDUSER("도와드려요"),

    FREE("자유게시판");

    private String postCategory;

    private final String displayValue;

    PostCategory(String displayValue) {
        this.displayValue = displayValue;
    }



}