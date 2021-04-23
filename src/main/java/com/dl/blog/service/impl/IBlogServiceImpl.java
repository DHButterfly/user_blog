package com.dl.blog.service.impl;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.mapper.BlogMapper;
import com.dl.blog.mapper.BlogTagMapper;
import com.dl.blog.mapper.BlogTagsUnionMapper;
import com.dl.blog.mapper.BlogTypeMapper;
import com.dl.blog.pojo.*;
import com.dl.blog.service.IBlogService;
import com.dl.blog.util.MarkdownToHTMLUtil;
import com.dl.blog.util.StringToListUtil;
import com.dl.blog.vo.ManageBlogDetailsVO;
import com.dl.blog.vo.ManageBlogListVO;
import com.dl.blog.vo.PreEndBlogListVO;
import com.dl.blog.vo.PreEndBlogRecommendVO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("IBlogService")
public class IBlogServiceImpl implements IBlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagsUnionMapper blogTagsUnionMapper;
    @Autowired
    private BlogTypeMapper typeMapper;
    @Override
    public List<ManageBlogListVO> selectAllBlogs() {
        return blogMapper.selectAllBlogs();
    }

    @Override
    public List<ManageBlogListVO> selectByConditions(String title, Integer typeId, Integer recommend) {
        return blogMapper.selectByConditions(title, typeId, recommend);
    }

    @Override
    public ServerResponse<String> insertBlog(Blog blog,String tagIds) {
        List<String> tagList= StringToListUtil.convertToList(tagIds);
        tagIds="";
        boolean flag=false;
        int cnt=0;
        for(String tag:tagList){
            cnt++;
            if(StringToListUtil.isNumeric(tag)){
                tagIds+=tag;
                flag=true;
            }else{//插入blogtag数据库,将返回的id拼接在后面
                BlogTag blogTag=new BlogTag();
                blogTag.setName(tag);
                int count=blogTagMapper.insert(blogTag);//返回的是影响记录的行数
                int id=blogTag.getId();
                if(id!=0) tagIds+=id;
            }
            if(cnt==tagList.size()) break;
            if(flag){
                flag=false;
                tagIds+=",";
            }
        }
        System.out.println("tagIds:"+tagIds);
        //判断一下有没有id，有则保存，无则更新
        BlogTagsUnion blogTagsUnion=new BlogTagsUnion();
        blogTagsUnion.setBid(blog.getId());
        blogTagsUnion.setTid(tagIds);
        int ansBlog=0,ansBlogUnionTags=0;
        if(blog.getId()==null){
            ansBlog=blogMapper.insert(blog);
            blogTagsUnion.setBid(blog.getId());
            ansBlogUnionTags=blogTagsUnionMapper.insert(blogTagsUnion);
        }else{
            //创建时间、以及浏览次数需要查寻数据库得到再save
            ManageBlogDetailsVO blogDetailsVO=blogMapper.selectBeforeUpdateBlog(blog.getId());
            blog.setViews(blogDetailsVO.getViews());
            blog.setCreateTime(blogDetailsVO.getCreateTime());
            ansBlog=blogMapper.updateByPrimaryKeySelective(blog);
            ansBlogUnionTags=blogTagsUnionMapper.updateByPrimaryKeySelective(blogTagsUnion);
        }
        if(ansBlog!=0&&ansBlogUnionTags!=0){
            return ServerResponse.createBySuccessMessage("博客发布成功！");
        }
        return ServerResponse.createByErrorMessage("博客发布失败！");
    }

    @Override
    public ServerResponse<String> deleteBlog(Integer id) {
        int ans=blogMapper.deleteByPrimaryKey(id);
        if(ans!=0){
            return ServerResponse.createBySuccessMessage("博客数据删除成功！");
        }
        return ServerResponse.createByErrorMessage("博客数据删除失败！");
    }

    @Override
    public ServerResponse<String> updateBlog(Blog blog,String tagIds) {
        int ansBlog=blogMapper.updateByPrimaryKeySelective(blog);
        BlogTagsUnion blogTagsUnion=new BlogTagsUnion();
        blogTagsUnion.setBid(blog.getId());
        blogTagsUnion.setTid(tagIds);

        int ansTagUnions=blogTagsUnionMapper.updateByPrimaryKeySelective(blogTagsUnion);
        //blogTag表

        return null;
    }

    @Override
    public ServerResponse<ManageBlogDetailsVO> selectManageBlogDetailsById(Integer id) {
        ManageBlogDetailsVO blogDetailsVO=blogMapper.selectManageDetailsById(id);
        return ServerResponse.createBySuccess(blogDetailsVO);
    }

    @Override
    public List<PreEndBlogListVO> selectPreEndBlogList(Integer typeId) {
        return blogMapper.selectPreEndBlogList(typeId);
    }

    @Override
    public List<PreEndBlogRecommendVO> selectPreLastRecommend() {
        return blogMapper.selectLastRecommend();
    }
    //因为需要更新views，因此命名为update，否则数据源只读
    @Override
    public ServerResponse<ManageBlogDetailsVO> updatePreBlogDetails(Integer id) {
        //views字段需要处理一下，即更新该博客的views+1
        ManageBlogDetailsVO blogVO=blogMapper.selectBeforeUpdateBlog(id);
        Integer views=blogVO.getViews()+1;
        //更新博客浏览次数
        int ans=blogMapper.updateViewsById(views,id);
        ManageBlogDetailsVO blogDetailsVO=blogMapper.selectPreBlogDetails(id);
        if(blogDetailsVO==null||ans==0) return ServerResponse.createByErrorMessage("该博客不存在，可能已经删除！");
        String convertContent=MarkdownToHTMLUtil.markdownToHtmlExtensions(blogDetailsVO.getContent());
        blogDetailsVO.setContent(convertContent);
        //查询标签数据
        String blogTagIds=blogTagsUnionMapper.selectTagsById(blogDetailsVO.getId());
        if(blogTagIds!=null){
            String []blogTags=blogTagIds.split(",");
            List<String> blogTagNames=new ArrayList<>();
            for(String blogTag:blogTags){
                blogTagNames.add(blogTagMapper.selectTagNameById(Integer.parseInt(blogTag)));
            }
            blogDetailsVO.setTags(blogTagNames);
        }
        return ServerResponse.createBySuccess(blogDetailsVO);
    }

    @Override
    public List<PreEndBlogListVO> selectBlogListByTagId(Integer tagId) {
        //首先根据tagId查询到相应的博客列表，或者直接做一次联合查询，这里因为pagehelper原因，所以直接一次联合查询
        //查询标签数据
        List<PreEndBlogListVO> preEndBlogListVOS =blogMapper.selectBlogByTagId(tagId);
        for(PreEndBlogListVO preEndBlogListVO:preEndBlogListVOS){
            String blogTagIds=blogTagsUnionMapper.selectTagsById(preEndBlogListVO.getId());
            if(blogTagIds!=null){
                String []blogTags=blogTagIds.split(",");
                List<String> blogTagNames=new ArrayList<>();
                for(String blogTag:blogTags){
                    blogTagNames.add(blogTagMapper.selectTagNameById(Integer.parseInt(blogTag)));
                }
                preEndBlogListVO.setTags(blogTagNames);
            }
        }
        return preEndBlogListVOS;
    }

    @Override
    public List<List<PreEndBlogListVO>> selectAllArchiveBlog() {
        List<PreEndBlogListVO> preEndBlogListVOS=blogMapper.selectAllArchiveBlog();
        List<List<PreEndBlogListVO>> preEndBlogList=new LinkedList<>();//记录所有归档数据
        Map<String, Boolean> maps=new HashMap<>();
        List<PreEndBlogListVO> list=new LinkedList<>();//添加用的比较多,记录当前年的归档数据
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int cnt=0;
        for(PreEndBlogListVO preEndBlogListVO:preEndBlogListVOS){
            String dateString = formatter.format(preEndBlogListVO.getUpdateTime());
            String []str=dateString.split("-");
            if(maps.get(str[0])==null){ //第一次添加，将上一年数据存入
                maps.put(str[0],true);
                //将当前信息添加到List中，最后添加到返回数据的List<List>
                if(cnt!=0) preEndBlogList.add(list);//将上一年数据存入
                cnt++;
                list=new LinkedList<>();
                list.add(preEndBlogListVO);
            }else{
                list.add(preEndBlogListVO);
            }
        }
        preEndBlogList.add(list);
        return preEndBlogList;
    }


}
