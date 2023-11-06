package kr.co.imguru.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("남자"),

    FEMALE("여자");

    String gender;

//    public static Gender of(String gender) {
//        return Arrays.stream(Gender.values())
//                .filter(type -> type.toString().equalsIgnoreCase(gender))
//                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_MEMBER_GENDER_NOT_FOUND));
//    }

}