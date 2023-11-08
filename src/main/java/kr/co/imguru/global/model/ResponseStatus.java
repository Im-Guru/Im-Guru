package kr.co.imguru.global.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseStatus {

    // Success Status
    SUCCESS_OK("요청이 성공적으로 처리되었습니다.", HttpStatus.OK),
    SUCCESS_CREATE("요청이 성공적으로 처리되어 새로운 리소스가 생성되었습니다.", HttpStatus.CREATED),
    SUCCESS_ACCEPTED("요청이 성공적으로 처리되었지만, 결과가 아직 완료되지 않았습니다.", HttpStatus.ACCEPTED),
    SUCCESS_NO_CONTENT("요청이 성공적으로 처리되었지만, 응답 데이터가 없습니다.", HttpStatus.NO_CONTENT),

    // Failed Status
    FAIL_BAD_REQUEST("클라이언트의 요청이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    FAIL_UNAUTHORIZED("클라이언트가 인증되지 않았습니다.", HttpStatus.UNAUTHORIZED),
    FAIL_FORBIDDEN("클라이언트가 요청한 리소스에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    FAIL_NOT_FOUND("클라이언트가 요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_METHOD_NOT_ALLOWED("클라이언트가 요청한 HTTP 메소드가 허용되지 않았습니다.", HttpStatus.METHOD_NOT_ALLOWED),
    FAIL_INVALID_PARAMETER("파라미터 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    FAIL_ILLEGAL_ACCESS("파라미터 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    // Member Failed Status
    FAIL_MEMBER_NOT_FOUND("클라이언트가 요청한 소유자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_MEMBER_GENDER_NOT_FOUND("클라이언트가 요청한 소유자의 성별을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_MEMBER_ROLE_NOT_FOUND("클라이언트가 요청한 소유자의 권한을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_MEMBER_EMAIL_DUPLICATED("클라이언트가 요청한 이메일이 중복되었습니다.", HttpStatus.BAD_REQUEST),
    FAIL_MEMBER_TELEPHONE_DUPLICATED("클라이언트가 요청한 전화번호가 중복되었습니다.", HttpStatus.BAD_REQUEST),
    FAIL_MEMBER_NICKNAME_DUPLICATED("클라이언트가 요청한 닉네임이 중복되었습니다.", HttpStatus.BAD_REQUEST),
    FAIL_MEMBER_PASSWORD_NOT_MATCHED("클라이언트가 입력한 비밀번호가 소유자의 비밀번호와 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    // Post
    FAIL_POST_NOT_FOUND("클라이언트가 요청한 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_POST_CATEGORY_NOT_FOUND("클라이언트가 요청한 게시글의 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_POST_LIKE_MEMBER_DUPLICATED("이미 좋아요를 누른 게시물입니다.", HttpStatus.BAD_REQUEST),
    FAIL_POST_WRITER_NOT_MATCH("해당 게시글의 작성자만이 수정할 수 있습니다.", HttpStatus.BAD_REQUEST),

    // Reply
    FAIL_REPLY_NOT_FOUND("클라이언트가 요청한 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_REPLY_LIKE_MEMBER_DUPLICATED("이미 좋아요를 누른 댓글입니다.", HttpStatus.BAD_REQUEST),

    // Report
    FAIL_REPORT_NOT_FOUND("클라이언트가 요청한 신고를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_REPORT_DUPLICATED("클라이언트가 요청한 신고는 이미 접수되었습니다. 중복 신고는 불가능합나디.", HttpStatus.BAD_REQUEST),
    FAIL_REPORT_CATEGORY_NOT_FOUND("클라이언트가 요청한 신고의 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // Skill
    FAIL_SKILL_NOT_FOUND("클라이언트가 요청한 스킬을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FAIL_SKILL_NAME_DUPLICATED("클라이언트가 요청한 스킬이 중복되었습니다.", HttpStatus.BAD_REQUEST),
    FAIL_SKILL_OUT_OF_BOUND("클라이언트가 요청한 스킬의 갯수가 초과되었습니다.", HttpStatus.BAD_REQUEST),

    // Login Failed Status
    FAIL_LOGIN_NOT_SUCCESS("로그인이 되지 않았습니다. 재시도 해주세요.", HttpStatus.BAD_REQUEST),

    // Token Failed Status
    FAIL_TOKEN_NOT_FOUND("클라이언트가 요청한 토큰 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    FAIL_REFRESHTOKEN_NOT_FOUND("클라이언트가 요청한 RefreshToken을 찾을 수 없습니다.(만료)", HttpStatus.NOT_FOUND);


    private String message;

    private HttpStatus statusCode;
}
