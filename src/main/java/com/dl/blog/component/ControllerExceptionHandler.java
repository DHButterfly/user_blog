package com.dl.blog.component;
import com.dl.blog.component.exception.UserNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *自定义异常处理&返回定制json数据；
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger= LoggerFactory.getLogger(ControllerExceptionHandler.class);
    //这种方法浏览器和其他客户端(adrioid等)返回的都是json数据，没有自适应效果
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String,Object> handlerException(Exception ex){
//        Map<String,Object>maps=new HashMap<>();
//        maps.put("code","user.notexist");
//        maps.put("message",ex.getMessage());
//        return maps;//利用responsebody返回响应json数据
//    }
    @ExceptionHandler(Exception.class)//要处理的异常类
    public String handlerException(Exception ex, HttpServletRequest request) throws Exception{
        logger.error("Request URL {} exception {}",request.getRequestURL(),ex);
        Map<String,Object>maps=new HashMap<>();
        if(AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class)!=null){
            throw ex;
        }
        //传入自己的状态码
        request.setAttribute("javax.servlet.error.status_code",500);
        maps.put("code","user not exist!!!");
        maps.put("message",ex.getMessage());
        maps.put("url",request.getRequestURL());
        request.setAttribute("ext",maps);
        return "forward:/error";//转发到/error请求，basicerrorcontroller会进行自适应处理,但是默认状态码是200
    }
}
