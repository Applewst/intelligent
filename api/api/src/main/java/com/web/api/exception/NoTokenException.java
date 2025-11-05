package com.web.api.exception;

import com.web.api.error.Error;

// 无Token异常
public class NoTokenException extends RuntimeException {
    public NoTokenException(String message) {
        super(Error.NOTOKEN.toString());
    }
}
