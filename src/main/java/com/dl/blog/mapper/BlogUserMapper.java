package com.dl.blog.mapper;

import com.dl.blog.pojo.BlogUser;

public interface BlogUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogUser record);

    int insertSelective(BlogUser record);

    BlogUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogUser record);

    int updateByPrimaryKey(BlogUser record);
    int checkUsername(String username);
    BlogUser selectLogin(String username,String password);

    BlogUser selectPreEndUserById(Integer id);

    BlogUser selectUserByNameAndEmail(String nickname,String email);
}