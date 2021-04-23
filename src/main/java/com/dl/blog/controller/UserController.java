package com.dl.blog.controller;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.component.exception.NotFoundException;
import com.dl.blog.pojo.BlogUser;
import com.dl.blog.service.IUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Scanner;

/**
 * 使用thymeleaf渲染页面是后端渲染，后端直接推送的是整个html文档，缺点是服务器压力增大，渲染的东西比较多
 * 因此采用ajax直接渲染页面数据
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<BlogUser> login(String username, String password, HttpSession session){
        ServerResponse<BlogUser> response=userService.checkLogin(username,password);
        if(response.isSuccess()){
            session.setAttribute(username+session.getId(),response.getData());
            System.out.println(username+session.getId()+"登陆成功!");
            session.setMaxInactiveInterval(10 * 60);//设置session有效时间为5分钟
        }
        return response;
    }
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest request, HttpSession session){
        session.removeAttribute(request.getParameter("token")+session.getId());
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "changepwd.do",method = RequestMethod.POST)
    public String changePwd(HttpSession session,HttpServletRequest request){
        return "/backstage/manage_type";
    }
}
