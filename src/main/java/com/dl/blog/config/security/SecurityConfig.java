package com.dl.blog.config.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //security配置角色访问页面问题
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/backstage/**").hasRole("200");

        //没有权限默认会到登录页面，需要开启登录的页面
        http.formLogin();
//        http.csrf().disable();
    }
    //认证


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser()   数据库方式

        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("tonghua").password(new BCryptPasswordEncoder().encode("123456")).roles("200");
//                .and()
//                .withUser()
    }

    public abstract class Father {
        abstract String getName();
        private String name;
        public void setAge(){};
        public void setName(){};
    }

    class Son extends Father{

        @Override
        String getName() {
            return null;
        }
    }

}
