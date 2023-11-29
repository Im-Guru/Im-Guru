package kr.co.imguru.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportCategory {

    ABUSE("욕설"),

    DEFAMATION("명예훼손"),

    PORNOGRAPHY("음란물"),

    ADVERTISEMENT("광고"),

    INCONGRUITY("부적합"),

    SPAMMER("도배");

    String reportCategory;

//    public static ReportCategory of(String reportCategory) {
//        return Arrays.stream(ReportCategory.values())
//                .filter(type -> type.toString().equalsIgnoreCase(reportCategory))
//                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_REPORT_CATEGORY_NOT_FOUND));
//    }

}
