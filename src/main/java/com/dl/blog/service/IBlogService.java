package com.dl.blog.service;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.pojo.Blog;
import com.dl.blog.pojo.BlogComment;
import com.dl.blog.vo.ManageBlogDetailsVO;
import com.dl.blog.vo.ManageBlogListVO;
import com.dl.blog.vo.PreEndBlogListVO;
import com.dl.blog.vo.PreEndBlogRecommendVO;

import java.util.List;

public interface IBlogService {

    List<ManageBlogListVO> selectAllBlogs();
    List<ManageBlogListVO> selectByConditions(String title, Integer typeId, Integer recommend);

    ServerResponse<String> insertBlog(Blog blog,String tagIds);

    ServerResponse<String> deleteBlog(Integer id);

    ServerResponse<String> updateBlog(Blog blog,String tagIds);

    ServerResponse<ManageBlogDetailsVO> selectManageBlogDetailsById(Integer id);

    List<PreEndBlogListVO> selectPreEndBlogList(Integer typeId);

    List<PreEndBlogRecommendVO> selectPreLastRecommend();

    ServerResponse<ManageBlogDetailsVO> updatePreBlogDetails(Integer id);

    List<PreEndBlogListVO> selectBlogListByTagId(Integer tagId);

    List<List<PreEndBlogListVO>> selectAllArchiveBlog();

}
