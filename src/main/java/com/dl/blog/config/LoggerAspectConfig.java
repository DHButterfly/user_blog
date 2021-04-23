package com.dl.blog.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 使用@Order注解指定切面的优先级，值越小优先级越高。
 */
@Order(1)
@Aspect
@EnableAspectJAutoProxy
@Component
public class LoggerAspectConfig {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /**@Pointcut("@annotation(* com.dl.blog.config.MyLogger)")
     * MyLogger：自定义注解所在全类名,然后在对应的类上加上@MyLogger注解就好了
     */


    @Pointcut("execution(* com.dl.blog.controller.*.*(..))")
    public void aspect(){ }

    /**
     * 配置前置通知
     * @param joinPoint 切面对象，可用于获取当前被代理类
     */
    @Before("aspect()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String classMethod=className+"."+method;
        Object[] args=joinPoint.getArgs();//请求参数
        RequestAspect requestAspect=new RequestAspect(url,ip,classMethod,args);
//        logger.info(className + "." + method + "(" + StringUtils.join(joinPoint.getArgs(), ",") + ")");
        logger.info("Request:{}",requestAspect);
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("aspect()")
    public void after(){
        logger.info("------------------doAfter------------------");
    }

    /**
     * 后置异常通知
     * @param joinPoint
     */
    @AfterThrowing("aspect()")
    public void throwss(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String classMethod=className+"."+method;
        logger.info("{}方法执行异常！！！",classMethod);
    }
    //处理完请求返回内容
    @AfterReturning(returning = "result",pointcut = "aspect()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     */
    @Around("aspect()")
    public Object arround(ProceedingJoinPoint pjp) {
        try {
            Object o =  pjp.proceed();
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
    //返回结果内部类
    private class RequestAspect{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestAspect(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        public RequestAspect() {
        }

        @Override
        public String toString() {
            return "RequestAspect{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
