package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 用户名已存在异常
 * @author Askr-Yggdrasill
 */
public class SameUserNameException extends RuntimeException {
    public SameUserNameException() {
        super(Error.SAMEUSERNAME.toString());
    }
}
