package com.dl.blog.service;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogUser;

public interface IUserService {
    public ServerResponse<BlogUser> checkLogin(String username,String password);
}
