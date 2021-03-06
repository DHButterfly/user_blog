package com.dl.blog.controller;

import com.dl.blog.common.ResponseCode;
import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.*;
import com.dl.blog.service.IBlogService;
import com.dl.blog.service.ITagService;
import com.dl.blog.service.ITypeService;
import com.dl.blog.vo.ManageBlogDetailsVO;
import com.dl.blog.vo.ManageBlogListVO;
import com.dl.blog.vo.PreEndBlogListVO;
import com.dl.blog.vo.PreEndBlogRecommendVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.ResponseWrapper;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blogs/")
public class BlogController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;

    @ResponseBody
    @RequestMapping(value = "get_blog.do",method = RequestMethod.POST)
    public ServerResponse<PageInfo> getBlogsByConditions(@RequestParam(name= "pageNum",defaultValue = "1",required = false)String pageNum,
                                                         @RequestParam(name = "pageSize",defaultValue = "5",required = false) String pageSize,
                                                         @RequestParam(name = "title",defaultValue = "",required = false) String title,
                                                         @RequestParam(name = "typeId",defaultValue = "0",required = false) Integer typeId,
                                                         @RequestParam(name= "recommend",defaultValue = "0",required = false) Integer recommend,
                                                         @RequestParam(name="isSearch",defaultValue = "0",required = false) Integer isSearch,
                                                         @RequestParam(value="token") String token, HttpSession session){

        BlogUser currentUser= (BlogUser) session.getAttribute(token+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        Integer pageNum1=Integer.parseInt(pageNum);
        Integer pageSize1=Integer.parseInt(pageSize);
        //???????????????????????????PageHelper???????????????????????????????????????????????????????????????
        PageHelper.startPage(pageNum1, pageSize1);
        List<ManageBlogListVO> blogs=new ArrayList<>();
        if(isSearch==0) blogs=blogService.selectAllBlogs();
        else blogs=blogService.selectByConditions(title,typeId,recommend);
        //??????PageInfo?????????????????????????????????pageInfo?????????????????????
        PageInfo pageInfo = new PageInfo<ManageBlogListVO>(blogs, pageSize1);
        ServerResponse<PageInfo> response=ServerResponse.createBySuccess(pageInfo);
        return response;
    }
    @ResponseBody
    @RequestMapping(value="get_all_type.do",method = RequestMethod.GET)
    public ServerResponse<List<BlogType>> getAllType(String token,HttpSession session){
        BlogUser currentUser = (BlogUser) session.getAttribute(token + session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        List<BlogType> blogTypes=typeService.selectAllType();
        return ServerResponse.createBySuccess(blogTypes);
    }

    @ResponseBody
    @RequestMapping(value = "get_all_tag.do",method = RequestMethod.GET)
    public ServerResponse<List<BlogTag>> getAllTag(String token,HttpSession session){
        BlogUser user= (BlogUser) session.getAttribute(token+session.getId());
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        List<BlogTag> blogTagList=tagService.selectBlogTags();
        return ServerResponse.createBySuccess(blogTagList);
    }

    //???JSON??????Java????????????@RequestBody ????????????Java???????????????JSON??????@ResponseBody??????
    @ResponseBody
    @RequestMapping(value = "save_all_blog.do",method = RequestMethod.POST)
    public ServerResponse<String> saveBlog(Blog blog,String tagIds, String token, HttpSession session){
        BlogUser blogUser = (BlogUser) session.getAttribute(token+session.getId());
        blog.setViews(0);
        blog.setUserId(blogUser.getId());
        if(blogUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        ServerResponse<String> response=blogService.insertBlog(blog,tagIds);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "delete_blog.do",method = RequestMethod.POST)
    public ServerResponse<String> deleteBlog(Integer id,String token,HttpSession session){
        BlogUser blogUser = (BlogUser) session.getAttribute(token+session.getId());
        if(blogUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        ServerResponse<String> response=blogService.deleteBlog(id);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "get_blog_details.do",method = RequestMethod.POST)
    public ServerResponse<ManageBlogDetailsVO> getBlogDetails(Integer id, String token, HttpSession session){
        BlogUser blogUser = (BlogUser) session.getAttribute(token+session.getId());
        if(blogUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"???????????????????????????");
        }
        ServerResponse<ManageBlogDetailsVO> response=blogService.selectManageBlogDetailsById(id);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "get_pre_bloglist.do",method = RequestMethod.POST)
    public ServerResponse<PageInfo> getPreBlogList(@RequestParam(name = "pageNum",defaultValue = "1",required = false) String pageNum,
                                                   @RequestParam(name = "pageSize",defaultValue = "5",required = false) String pageSize,
                                                   @RequestParam(name = "typeId",defaultValue = "0",required = false) Integer typeId,
                                                   @RequestParam(name = "tagId",defaultValue = "0",required = false) Integer tagId){
        Integer pageNum1=Integer.parseInt(pageNum);
        Integer pageSize1=Integer.parseInt(pageSize);
        //???????????????????????????PageHelper???????????????????????????????????????????????????????????????
        PageHelper.startPage(pageNum1, pageSize1);
        List<PreEndBlogListVO> blogs=new ArrayList<>();
        if(tagId==0) blogs=blogService.selectPreEndBlogList(typeId);
        else blogs=blogService.selectBlogListByTagId(tagId);//??????????????????
        PageInfo pageInfo = new PageInfo<PreEndBlogListVO>(blogs, pageSize1);
        ServerResponse<PageInfo> response=ServerResponse.createBySuccess(pageInfo);
        return response;
    }

    @ResponseBody
    @RequestMapping(value="get_pre_recommend.do",method = RequestMethod.POST)
    public ServerResponse<List<PreEndBlogRecommendVO>> getPreLastRecommend(){
        return ServerResponse.createBySuccess(blogService.selectPreLastRecommend());
    }


    @ResponseBody
    @RequestMapping(value = "get_pre_details.do",method = RequestMethod.POST)
    public ServerResponse<ManageBlogDetailsVO> getPreBlogDetails(Integer id){
        return blogService.updatePreBlogDetails(id);
    }

    @ResponseBody
    @RequestMapping(value = "get_blog_archive.do",method = RequestMethod.POST)
    public ServerResponse<List<List<PreEndBlogListVO>>> getAllArchiveBlog(){
        return ServerResponse.createBySuccess(blogService.selectAllArchiveBlog());
    }

    @ResponseBody
    @RequestMapping(value = "get_blog_tag.do",method = RequestMethod.POST)
    public ServerResponse<List<PreEndBlogListVO>>getAllTagBlog(){
        return null;
    }

}
