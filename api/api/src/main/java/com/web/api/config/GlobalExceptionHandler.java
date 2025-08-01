package com.web.api.config;

import com.web.api.exception.NoFindAccountException;
import com.web.api.exception.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pojo.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限不足: PermissionException
     */
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<Result> handlePermissionException(PermissionException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 登录失败: NoFindAccountException
     */
    @ExceptionHandler(NoFindAccountException.class)
    public ResponseEntity<Result> handleNoFindAccountException(NoFindAccountException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.error(e.getMessage()));
    }
}
