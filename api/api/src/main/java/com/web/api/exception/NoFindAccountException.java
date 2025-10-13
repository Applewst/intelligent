package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 登录失败
 */
public class NoFindAccountException extends RuntimeException {
    public NoFindAccountException() {
        super(Error.NOACCOUNT.toString());
    }
}
