package com.dl.blog;

import com.dl.blog.config.db.EnableDynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//SecurityAutoConfiguration.class
@PropertySources({ @PropertySource("classpath:application-jdbc.properties"),
        @PropertySource("classpath:redis.properties")
})
//开启异步任务
@EnableAsync
//启动全局事务
@EnableTransactionManagement
@MapperScan(basePackages = "com.dl.blog.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
@EnableDynamicDataSource  //自定义注解
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
