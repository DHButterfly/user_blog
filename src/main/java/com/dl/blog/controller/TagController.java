package com.dl.blog.controller;

import com.dl.blog.common.ResponseCode;
import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogTag;
import com.dl.blog.pojo.BlogType;
import com.dl.blog.pojo.BlogUser;
import com.dl.blog.service.ITagService;
import com.dl.blog.vo.PreEndBlogTagsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tag/")
public class TagController {


    @Autowired
    private ITagService tagService;

    @ResponseBody
    @RequestMapping(value = "get_blogtag.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> getAllBlogType(@RequestParam(name="pageNum",defaultValue = "1",required = false)String pageNum,
                                                   @RequestParam(name = "pageSize",defaultValue = "5",required = false) String pageSize,
                                                   @RequestParam(value="token") String token, HttpSession session){
        BlogUser user=(BlogUser)session.getAttribute(token+session.getId());
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        Integer pageNum1=Integer.parseInt(pageNum);
        Integer pageSize1=Integer.parseInt(pageSize);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum1, pageSize1);//Integer pageNum,Integer pageSize
        List<BlogTag> blogTags=tagService.selectBlogTags();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<BlogTag>(blogTags, pageSize1);
        ServerResponse<PageInfo> response=ServerResponse.createBySuccess(pageInfo);
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "delete_blogtag.do",method = RequestMethod.POST)
    public ServerResponse<String> deleteTag(Integer id, HttpServletRequest request, HttpSession session){
        BlogUser currentUser=(BlogUser)session.getAttribute(request.getParameter("token")+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        ServerResponse<String> response=tagService.deleteTagById(id);
        if(response.isSuccess()){
            return response;
        }
        return ServerResponse.createByErrorMessage("标签数据删除失败！");
    }

    @ResponseBody
    @RequestMapping(value="save_blogtag.do",method = RequestMethod.POST)
    public ServerResponse<String> insertTag(BlogTag blogTag,HttpServletRequest request,HttpSession session){
        BlogUser currentUser=(BlogUser)session.getAttribute(request.getParameter("token")+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        return tagService.saveBlogTag(blogTag);
    }


    @ResponseBody
    @RequestMapping(value="get_tag_toNum.do",method = RequestMethod.GET)
    public ServerResponse<List<PreEndBlogTagsVO>> getPreTagsNum(){
        return tagService.selectPreTagsNum();
    }

}
