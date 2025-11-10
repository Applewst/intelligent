package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 无ID异常
 */
public class NoIdException extends RuntimeException {
    public NoIdException() {
        super(Error.NOID.toString());
    }
}
