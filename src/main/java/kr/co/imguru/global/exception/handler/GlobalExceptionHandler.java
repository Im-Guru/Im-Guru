package kr.co.imguru.global.exception.handler;


import kr.co.imguru.global.exception.DuplicatedException;
import kr.co.imguru.global.exception.IllegalArgumentException;
import kr.co.imguru.global.exception.NotFoundException;
import kr.co.imguru.global.exception.UnauthorizedException;
import kr.co.imguru.global.model.ResponseErrorFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//전역적으로 예외 처리를 담당하는 클래스
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    protected ResponseEntity<ResponseErrorFormat> handleDuplicatedException(DuplicatedException e) {
        log.warn("-------HandleDuplicatedException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_BAD_REQUEST.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorFormat);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseErrorFormat> handleNotFoundException(NotFoundException e) {
        log.warn("-------HandleNotFoundException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_NOT_FOUND.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorFormat);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ResponseErrorFormat> handleRuntimeException(RuntimeException e) {
        log.warn("-------HandleRuntimeException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_BAD_REQUEST.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorFormat);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ResponseErrorFormat> handleAuthenticationException(UnauthorizedException e) {
        log.warn("-------HandleAuthenticationException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_UNAUTHORIZED.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseErrorFormat);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ResponseErrorFormat> handleIllegalAccessException(IllegalArgumentException e) {
        log.warn("-------HandleIllegalArgumentException-------", e);

        ResponseErrorFormat responseErrorFormat = ResponseErrorFormat.builder()
                .message(e.getMessage())
                .statusCode(ResponseStatus.FAIL_ILLEGAL_ACCESS.getStatusCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorFormat);
    }

}
