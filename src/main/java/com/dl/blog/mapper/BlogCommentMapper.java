package com.dl.blog.mapper;

import com.dl.blog.pojo.BlogComment;
import com.dl.blog.vo.PreEndBlogCommentVO;

import java.util.List;

public interface BlogCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogComment record);

    int insertSelective(BlogComment record);

    BlogComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogComment record);

    int updateByPrimaryKeyWithBLOBs(BlogComment record);

    int updateByPrimaryKey(BlogComment record);

    List<PreEndBlogCommentVO> selectBlogCommentByBlogId(Integer blodId);

    List<PreEndBlogCommentVO> selectCommentByIdAndParentId(Integer blogId,Integer parentCommentId);

    BlogComment selectByNickName(String nickname);

}