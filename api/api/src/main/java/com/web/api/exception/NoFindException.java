package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 用户未找到异常
 */
public class NoFindException extends RuntimeException {
    public NoFindException() {
        super(Error.NOFINDUSER.toString());
    }
}
