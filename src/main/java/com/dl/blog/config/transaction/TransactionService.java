package com.dl.blog.config.transaction;



import com.dl.blog.config.db.DynamicDataSourceAutoConfiguration;
import com.dl.blog.config.db.TransactionAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@AutoConfigureAfter(TransactionAspect.class)
@Configuration
@EnableTransactionManagement()
public class TransactionService {

    @Autowired
    private DynamicDataSourceAutoConfiguration.DynamicDataSource dataSource;


    @Order(Ordered.LOWEST_PRECEDENCE)
    @Bean(name = "txManager")
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}

