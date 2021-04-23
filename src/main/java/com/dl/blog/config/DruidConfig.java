package com.dl.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020/5/16.
 */
@Configuration
public class DruidConfig {
    //将所有前缀为spring.datasource下的配置项都加载DataSource中进行参数绑定
    @ConfigurationProperties(prefix = "spring.datasource",ignoreInvalidFields=false, ignoreUnknownFields=true)
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }
    //配置druid的监控
    //配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams=new HashMap<>();
        initParams.put("loginUsername","admin");//配置后台管理员用户
        initParams.put("loginPassword","123456");
        initParams.put("allow","127.0.0.1");//不配置localhost允许所有访问
        initParams.put("deny","192.168.15.21");//拒绝谁访问,黑名单优先级更高
        bean.setInitParameters(initParams);
        return bean;
    }
    //配置一个web监控的filter
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams=new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*,/data/*");//对druid下的请求以及静态资源不拦截
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
