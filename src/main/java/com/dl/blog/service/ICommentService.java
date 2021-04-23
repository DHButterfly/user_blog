package com.dl.blog.service;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogComment;
import com.dl.blog.vo.PreEndBlogCommentVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICommentService {
    ServerResponse<String> saveBlogComment(BlogComment blogComment);
    List<PreEndBlogCommentVO> getBlogComment(Integer blogId);
}
