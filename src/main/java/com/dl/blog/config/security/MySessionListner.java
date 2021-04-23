package com.dl.blog.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//启动类加上注解@ServletComponentScan，这样才能扫描到监听器
@WebListener
public class MySessionListner implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(MySessionListner.class);

    /**
     * 新建session时（打开浏览器访问登录页面时，服务器会创建一个新的session）
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("新建session时");
    }
    /**
     * 删除session时（退出系统）
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("销毁session时");
        MyAuthenctiationSuccessHandler.sessionMap.remove(httpSessionEvent.getSession().getId());
    }

}