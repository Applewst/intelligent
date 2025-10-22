package com.web.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**状态码 */
    private Integer code;   //1成功,0失败
    /**提示信息 */
    private String message;
    /**返回数据 */
    private Object data;

    //成功响应,无返回
    public static Result success() {
        return new Result(1, "success", null);
    }

    //成功响应,带返回
    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    //成功响应,带返回和提示
    public static Result success(String msg, Object data) {
        return new Result(1, msg, data);
    }

    //失败返回,
    public static Result error(String msg){
        return new Result(0, msg, null);
    }
}
