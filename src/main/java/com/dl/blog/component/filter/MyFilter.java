package com.dl.blog.component.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 配置过滤器
 */
public class MyFilter implements Filter {
    private Logger logger= LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Filter before Process....");
        filterChain.doFilter(servletRequest,servletResponse);//放行
        logger.info("Filter after Process....");
    }

    @Override
    public void destroy() {

    }
}
