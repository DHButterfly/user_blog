package com.dl.blog.mapper;

import com.dl.blog.pojo.Blog;
import com.dl.blog.vo.ManageBlogDetailsVO;
import com.dl.blog.vo.ManageBlogListVO;
import com.dl.blog.vo.PreEndBlogListVO;
import com.dl.blog.vo.PreEndBlogRecommendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<ManageBlogListVO> selectAllBlogs();

    List<ManageBlogListVO> selectByConditions(String title, Integer typeId, Integer recommend);


    ManageBlogDetailsVO selectManageDetailsById(Integer id);

    ManageBlogDetailsVO selectBeforeUpdateBlog(Integer id);

    List<PreEndBlogListVO> selectPreEndBlogList(Integer typeId);

    List<PreEndBlogRecommendVO> selectLastRecommend();

    ManageBlogDetailsVO selectPreBlogDetails(Integer id);

    int updateViewsById(Integer views,Integer id);

    List<PreEndBlogListVO> selectBlogListByTypeId(@Param("typeId") Integer typeId);

    List<PreEndBlogListVO> selectAllArchiveBlog();

    List<PreEndBlogListVO> selectBlogByTagId(Integer tagId);
}