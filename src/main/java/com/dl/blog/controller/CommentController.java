package com.dl.blog.controller;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogComment;
import com.dl.blog.service.ICommentService;
import com.dl.blog.util.UserAvatarUtil;
import com.dl.blog.vo.PreEndBlogCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/comment/")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @ResponseBody
    @RequestMapping(value= "save_blog_comment.do",method = RequestMethod.POST)
    public ServerResponse<String> saveBlogComment(BlogComment blogComment){
        return commentService.saveBlogComment(blogComment);
    }

    @ResponseBody
    @RequestMapping(value="get_blog_comment.do",method = RequestMethod.POST)
    public ServerResponse<List<PreEndBlogCommentVO>> getBlogComment(Integer blogId){
        List<PreEndBlogCommentVO> blogCommentList=commentService.getBlogComment(blogId);
        return blogCommentList==null?ServerResponse.createByErrorMessage("该博客暂无评论信息！"):ServerResponse.createBySuccess(blogCommentList);
    }
}
