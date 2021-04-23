package com.dl.blog.mapper;

import com.dl.blog.pojo.BlogTag;

import java.util.List;

public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> selectAllTag();

    BlogTag selectByTagName(String name);
    String selectTagNameById(Integer id);
}