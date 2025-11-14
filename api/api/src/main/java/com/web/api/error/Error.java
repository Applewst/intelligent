package com.web.api.error;

/**
 * 错误枚举类
 * @author Askr-Yggdrasill
 */
public enum Error {

    //用户名已存在 SameUserNameException
    SAMEUSERNAME("用户名已存在，请更换后重试"),
    //权限不足 PermissionException
    NOPERMISSION("权限不足，请联系管理员"),
    //登录失败 NoFindAccountException
    NOACCOUNT("登录失败，账号或密码错误"),
    //登录状态异常
    NOTOKEN("登录状态异常，请重新登录"),
    //账号被封禁
    ACCOUNTBAN("账号被封禁，请联系管理员"),
    //该数据不存在: 传入虚假id
    NOFINDUSER("该数据不存在,请检查后重试"),
    //操作异常: 数据库操作异常
    ABNORMALOPERATION("操作异常，请稍后重试"),
    //未选择任何数据(id=null)
    NOID("未选择任何数据");

    private final String errorMessage;

    Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
