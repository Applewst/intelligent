package com.web.api.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于日志目标对象
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public  @interface Log {
}

