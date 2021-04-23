package com.dl.blog.controller;

import com.dl.blog.common.ResponseCode;
import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.BlogType;
import com.dl.blog.pojo.BlogUser;
import com.dl.blog.service.ITypeService;
import com.dl.blog.vo.PreEndBlogTypeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/type/")
public class TypeController {
    @Autowired
    private ITypeService typeService;

    @ResponseBody
    @RequestMapping(value = "get_blogtype.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> getAllBlogType(@RequestParam(name="pageNum",defaultValue = "1",required = false)String pageNum,
                                                   @RequestParam(name = "pageSize",defaultValue = "5",required = false) String pageSize,
                                                   @RequestParam(value="token") String token, HttpSession session){
        BlogUser currentUser=(BlogUser)session.getAttribute(token+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        Integer pageNum1=Integer.parseInt(pageNum);
        Integer pageSize1=Integer.parseInt(pageSize);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum1, pageSize1);//Integer pageNum,Integer pageSize
        List<BlogType> blogTypes=typeService.selectAllType();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<BlogType>(blogTypes, pageSize1);
        ServerResponse<PageInfo> response=ServerResponse.createBySuccess(pageInfo);
        return response;
    }
    @ResponseBody
    @RequestMapping(value = "add_blogtype.do",method = RequestMethod.POST)
    public ServerResponse<String> addType(BlogType blogType,HttpServletRequest request,HttpSession session){
        BlogUser currentUser= (BlogUser) session.getAttribute(request.getParameter("token")+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
        }
        //新增数据
        ServerResponse<String> response=typeService.insertType(blogType);
        return response;
    }


    @ResponseBody
    @RequestMapping(value = "delete_blogtype.do",method = RequestMethod.POST)
    public ServerResponse<String> deleteType(Integer id, HttpServletRequest request, HttpSession session){
        System.out.println("进入删除分类的接口！");
        BlogUser currentUser=(BlogUser)session.getAttribute(request.getParameter("token")+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        ServerResponse<String> response=typeService.deleteTypeById(id);
        if(response.isSuccess()){
            return response;
        }
        return ServerResponse.createByErrorMessage("数据删除失败！");
    }


    @ResponseBody
    @RequestMapping(value="update_blogtype.do",method = RequestMethod.POST)
    public ServerResponse<String> updateType(BlogType blogType,HttpServletRequest request, HttpSession session){
        System.out.println("接收参数："+blogType);
        BlogUser currentUser= (BlogUser) session.getAttribute(request.getParameter("token")+session.getId());
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }else if(!(currentUser.getType().equals(200))){
            return ServerResponse.createByErrorMessage("暂无权限操作！");
        }
        ServerResponse<String> response=typeService.updateType(blogType);
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "get_type_toNum.do",method = RequestMethod.GET)
    public ServerResponse<List<PreEndBlogTypeVO>> selectTypetoNum(){
        return typeService.selectTypeToNum();
    }

}

