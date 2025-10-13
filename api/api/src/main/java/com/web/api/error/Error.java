package com.web.api.error;

public enum Error {

    //权限不足 PermissionException
    NOPERMISSION("权限不足，请联系管理员"),
    //登录失败 NoFindAccountException
    NOACCOUNT("登录失败，账号或密码错误"),
    //登录状态异常
    NoToken("登录状态异常，请重新登录"),
    //账号被封禁
    ACCOUNTBAN("账号被封禁，请联系管理员");

    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
