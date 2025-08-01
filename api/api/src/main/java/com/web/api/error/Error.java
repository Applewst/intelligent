package com.web.api.error;

public enum Error {

    //权限不足 PermissionException
    NOPERMISSION("权限不足，请联系管理员"),
    //登录失败 NoFindAccountException
    NOACCOUNT("登录失败，账号或密码错误");

    private String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
