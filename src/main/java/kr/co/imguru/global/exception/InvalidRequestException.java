package kr.co.imguru.global.exception;


import kr.co.imguru.global.model.ResponseStatus;

/**
 * 클라이언트의 요청이 잘못되었을 때 사용하는 예외
 */
public class InvalidRequestException extends BusinessLogicException {

    public InvalidRequestException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
