package com.dl.blog.mapper;

import com.dl.blog.pojo.BlogType;
import com.dl.blog.vo.PreEndBlogTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogType record);

    int insertSelective(BlogType record);

    BlogType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogType record);

    int updateByPrimaryKey(BlogType record);

    List<BlogType> selectAllType();


    BlogType selectTypeName(String name);

    String selectNameById(Integer id);


    List<PreEndBlogTypeVO> selectTypeToCountList();
}