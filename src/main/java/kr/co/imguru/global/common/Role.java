package kr.co.imguru.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_ADMIN("관리자"),

    ROLE_MANAGER("매니저"),

    ROLE_GURU("도사"),

    ROLE_USER("회원");

    String userRole;

//    public static Role of(String userRole) {
//
//        return Arrays.stream(Role.values())
//                .filter(role -> role.toString().equalsIgnoreCase(userRole))
//                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_MEMBER_ROLE_NOT_FOUND));
//    }

}