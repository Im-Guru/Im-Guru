package kr.co.imguru.global.exception;


import kr.co.imguru.global.model.ResponseStatus;

/**
 * 인증된 사용자가 권한이 없는 리소스에 접근하려 할 때 사용하는 예외
 */
public class ForbiddenException extends BusinessLogicException {

    public ForbiddenException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
