package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 数据库操作异常
 */
public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException() {
        super(Error.ABNORMALOPERATION.toString());
    }
}
