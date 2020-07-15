package com.kakaopay.test.exception;

public class KakaoException extends RuntimeException{
    public KakaoException() {
        super();
    }

    public KakaoException(String message) {
        super(message);
    }

    public KakaoException(Throwable cause) {
        super(cause);
    }

    public KakaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
