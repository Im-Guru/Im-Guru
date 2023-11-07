package kr.co.imguru.global.exception;

import kr.co.imguru.global.model.ResponseStatus;

public class IllegalArgumentException extends BusinessLogicException{

    public IllegalArgumentException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
