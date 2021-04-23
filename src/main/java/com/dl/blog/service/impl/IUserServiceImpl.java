package com.dl.blog.service.impl;

import com.dl.blog.common.Const;
import com.dl.blog.common.ResponseCode;
import com.dl.blog.common.ServerResponse;
import com.dl.blog.mapper.BlogUserMapper;
import com.dl.blog.pojo.BlogUser;
import com.dl.blog.service.IUserService;
import com.dl.blog.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

@Service("IUserService")
public class IUserServiceImpl implements IUserService {

    Logger logger=LoggerFactory.getLogger(IUserServiceImpl.class);

    @Autowired
    private BlogUserMapper userMapper;

    @Override
    public ServerResponse<BlogUser> checkLogin(String username, String password) {
        logger.info(username+"进入后台登录接口！");
        int resultCount=userMapper.checkUsername(username);
        if(resultCount==0){
             return ServerResponse.createByErrorMessage("用户名不存在");
        }
        System.out.println("username:"+username+" password"+password+"Md5加密之后的密码："+MD5Util.MD5EncodeUtf8(password));
        BlogUser user=userMapper.selectLogin(username, MD5Util.MD5EncodeUtf8(password));
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误!");
        }
        user.setPassword(StringUtils.EMPTY);//将密码置空,防止暴露更多信息
        if(user.getType()== Const.Role.ROLE_MANAGER){
            logger.info("后台管理员登录成功！");
            return ServerResponse.createBySuccess("登陆成功",user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "当前用户无权限登录!");
    }



}
