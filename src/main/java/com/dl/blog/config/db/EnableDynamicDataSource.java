package com.dl.blog.config.db;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DynamicDataSourceAutoConfiguration.class)
public @interface EnableDynamicDataSource {

}
