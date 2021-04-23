package com.dl.blog.config;

import com.dl.blog.config.security.LoginIntercepter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${images.avatar}")
    private String imagesAvatar;
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer adapter=new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //添加视图映射
                //将/请求映射到login.html，视图解析器会自动加上后缀.html,详细查看ViewControllerRegistry
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");//首页映射
                registry.addViewController("/manage").setViewName("backstage/manage_login");//后台管理映射
            }
        };
        return adapter;
    }

    /**
     * 配置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(imagesAvatar,"classpath:/static/img/");
    }

    /**
     * 配置拦截器
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //  "/**"拦截任意多层路径下的请求,excludePathPatterns排除拦截资源路径
//        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**")
//                .excludePathPatterns("/index.html","/","/user/login.do","/manage","/css/**","/js/**");
//    }

    // 设置跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

}
