package com.dl.blog.service;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.Blog;
import com.dl.blog.pojo.BlogType;
import com.dl.blog.vo.PreEndBlogTypeVO;

import java.util.List;

public interface ITypeService {
    ServerResponse<String> deleteTypeById(Integer id);
    List<BlogType> selectAllType();
    ServerResponse<BlogType> selectById(Integer id);
    ServerResponse<String> insertType(BlogType blogType);
    ServerResponse<String> updateType(BlogType blogType);
    ServerResponse<List<PreEndBlogTypeVO>> selectTypeToNum();
}
