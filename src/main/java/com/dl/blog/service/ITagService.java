package com.dl.blog.service;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogTag;
import com.dl.blog.vo.PreEndBlogTagsVO;

import java.util.List;

public interface ITagService {
    List<BlogTag> selectBlogTags();
    ServerResponse<String> deleteTagById(Integer id);
    ServerResponse<String> saveBlogTag(BlogTag blogTag);

    ServerResponse<List<PreEndBlogTagsVO>> selectPreTagsNum();
}
