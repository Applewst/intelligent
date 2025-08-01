package com.web.api.exception;

import com.web.api.error.Error;

/**
 * 权限不足
 */
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super(Error.NOPERMISSION.toString());
    }
}
