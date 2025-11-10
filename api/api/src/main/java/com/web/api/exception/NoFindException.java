package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 数据库对象未找到异常(id存在，但未找到对应对象)
 */
public class NoFindException extends RuntimeException {
    public NoFindException() {
        super(Error.NOFINDUSER.toString());
    }
}
