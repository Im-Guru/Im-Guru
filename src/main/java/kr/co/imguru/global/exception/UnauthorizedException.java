package kr.co.imguru.global.exception;


import kr.co.imguru.global.model.ResponseStatus;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 사용하는 예외
 */
public class UnauthorizedException extends BusinessLogicException {

    public UnauthorizedException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
