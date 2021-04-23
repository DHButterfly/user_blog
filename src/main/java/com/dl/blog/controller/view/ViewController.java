package com.dl.blog.controller.view;


import com.dl.blog.common.Const;
import com.dl.blog.common.ResponseCode;
import com.dl.blog.common.ResponsePage;
import com.dl.blog.pojo.BlogUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ViewController {
    Logger logger= LoggerFactory.getLogger(ViewController.class);

    //后台首页跳转
    @RequestMapping(value="index.do",method = RequestMethod.GET)
    public String getIndexPage(HttpServletRequest request, HttpSession session){
        logger.info("进入index.do接口！");
        BlogUser user=(BlogUser) session.getAttribute(request.getParameter("token")+session.getId());
        if(user!=null){
            if(user.getType().intValue()== Const.Role.ROLE_MANAGER){
                return ResponsePage.Manage.MANAGE_PREINDEX;
            }
        }
        //用户未登录或者出现错误角色代码，则返回登录页面
        return ResponsePage.Manage.MANAGE_LOGIN;
    }

    //后台管理顶部导航跳转
    @RequestMapping(value="manage_nav.do",method = RequestMethod.GET)
    public String getManageTopNavPages(String id,HttpSession session,HttpServletRequest request){
        BlogUser user=(BlogUser) session.getAttribute(request.getParameter("token")+session.getId());
        if(user!=null){
            if(user.getType().intValue()== Const.Role.ROLE_MANAGER){
                if(id.equals("1")){   //博客管理
                    return ResponsePage.Manage.MANAGE_INDEX;
                }else if(id.equals("2")){ //类型
                    return ResponsePage.Manage.MANAGE_TYPE;
                }else if(id.equals("3")){  //标签
                    return ResponsePage.Manage.MANAGE_TAG;
                }else if(id.equals("4")){  //类型编辑页面
                    return ResponsePage.Manage.MANAGE_TYPE_INPUT;
                }else if(id.equals("5")){  //标签编辑页面
                    return ResponsePage.Manage.MANAGE_TAG_INPUT;
                }else{  //错误页面
                    return ResponsePage.Error.ERROR;
                }
            }
        }
        //登录页面
        return ResponsePage.Manage.MANAGE_LOGIN;
    }

    @RequestMapping(value = "manage_blog_edit.do",method = RequestMethod.GET)
    public String getBlogAddPage(String token,HttpSession session,HttpServletRequest request){
        BlogUser user = (BlogUser) session.getAttribute(request.getParameter("token") + session.getId());
        if(user!=null){
            if(user.getType().intValue()==Const.Role.ROLE_MANAGER){
                return ResponsePage.Manage.MANAGE_PUBLISH;  //发布页面
            }
        }
        return ResponsePage.Manage.MANAGE_LOGIN;  //登陆页面
    }

    //前台页面访问顶部跳转
    @RequestMapping(value = "pre_nav.do",method = RequestMethod.GET)
    public String getPreTopNavPages(String id){
        if(id.equals("1")){  //首页
            return ResponsePage.Web.INDEX;
        }else if(id.equals("2")){  //分类页面
            return ResponsePage.Web.CLASSIFICATION;
        }else if(id.equals("3")){ //标签页面
            return ResponsePage.Web.LABEL;
        }else if(id.equals("4")){ //归档页面
            return ResponsePage.Web.ARCHIVE;
        }else if(id.equals("5")){ //关于我页面
            return ResponsePage.Web.ABOUT;
        }else if(id.equals("6")){ //博客详情页面
            return ResponsePage.Web.BLOGDETAILS;
        }else{
            return ResponsePage.Error.ERROR;
        }
    }

}
