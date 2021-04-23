package com.dl.blog.config.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureAfter(MybatisConfig.class)
@MapperScan(basePackages = { "com.dl.blog.config.db" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisScanConfiguration {
    protected static Log log = LogFactory.getLog(MybatisScanConfiguration.class);

    public MybatisScanConfiguration() {
        log.info("*************************MybatisScanConfiguration***********************");
    }
}

