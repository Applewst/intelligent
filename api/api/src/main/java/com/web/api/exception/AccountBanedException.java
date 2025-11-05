package com.web.api.exception;

import com.web.api.error.Error;
import org.apache.shiro.authc.AuthenticationException;

/**
 * 账号被封禁
 */
public class AccountBanedException extends AuthenticationException {
    public AccountBanedException(String message) {
        super(Error.NOACCOUNT.toString());
    }
}
