package com.web.api.config;

import com.web.api.exception.AccountBaned;
import com.web.api.exception.NoFindAccountException;
import com.web.api.exception.PermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.web.api.pojo.Result;

import java.nio.file.AccessDeniedException;

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

    /**
     * 账号被封禁: AccountBanedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result> handleBanedAccountException(AccountBaned e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 其他运行时异常: RuntimeException
     */
    public ResponseEntity<Result> handleException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.error("服务器异常: " + e.getMessage()));
    }
}
