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

    String postCategory;

//    public static PostCategory of(String postCategory) {
//        return Arrays.stream(PostCategory.values())
//                .filter(type -> type.toString().equalsIgnoreCase(postCategory))
//                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_POST_CATEGORY_NOT_FOUND));
//    }

}
