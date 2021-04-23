package com.dl.blog.mapper;

import com.dl.blog.pojo.BlogTagsUnion;

import java.util.List;

public interface BlogTagsUnionMapper {
    int deleteByPrimaryKey(Integer bid);

    int insert(BlogTagsUnion record);

    int insertSelective(BlogTagsUnion record);

    BlogTagsUnion selectByPrimaryKey(Integer bid);

    int updateByPrimaryKeySelective(BlogTagsUnion record);

    int updateByPrimaryKey(BlogTagsUnion record);

    String selectTagsById(Integer bid);

    List<BlogTagsUnion> selectAllTagsUnion();

}