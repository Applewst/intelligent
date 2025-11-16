package com.web.api.config;

import com.web.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.web.api.pojo.Result;
import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理器
 * @author Askr-Yggdrasill
 */
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
     * 账号被封禁: AccountBanedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result> handleBanedAccountException(AccountBanedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 未找到账号异常: NoFindAccountException
     */
    @ExceptionHandler(NoFindAccountException.class)
    public ResponseEntity<Result> handleNoFindAccountException(NoFindAccountException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 未提供ID异常: NoIdException
     */
    @ExceptionHandler(NoIdException.class)
    public ResponseEntity<Result> handleNoIdException(NoIdException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 未提供令牌异常: NoTokenException
     */
    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<Result> handleNoTokenException(NoTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 未找到对象异常: NoFindException
     */
    @ExceptionHandler(NoFindException.class)
    public ResponseEntity<Result> handleNoFindException(NoFindException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 数据库操作异常: DatabaseOperationException
     */
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<Result> handleDatabaseOperationException(DatabaseOperationException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error(e.getMessage()));
    }

    /**
     * 用户名已存在异常: SameUserNameException
     */
    @ExceptionHandler(SameUserNameException.class)
    public ResponseEntity<Result> handleSameUserNameException(SameUserNameException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Result.error(e.getMessage()));
    }

//    /**
//     * 其他运行时异常: RuntimeException
//     */
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Result> handleException(RuntimeException e){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Result.error("错误: " + e.getMessage()));
//    }
}
