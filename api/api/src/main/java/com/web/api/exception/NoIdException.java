package com.web.api.exception;

import com.web.api.error.Error;

public class NoIdException extends RuntimeException {
    public NoIdException() {
        super(Error.NOID.toString());
    }
}
