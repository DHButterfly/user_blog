package com.dl.blog.service.impl;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.mapper.BlogCommentMapper;
import com.dl.blog.mapper.BlogUserMapper;
import com.dl.blog.pojo.BlogComment;
import com.dl.blog.pojo.BlogUser;
import com.dl.blog.service.ICommentService;
import com.dl.blog.util.UserAvatarUtil;
import com.dl.blog.vo.PreEndBlogCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Service("ICommentService")
public class ICommentServiceImpl implements ICommentService {

    @Autowired
    private BlogCommentMapper commentMapper;
    @Autowired
    private BlogUserMapper userMapper;
    @Override
    public ServerResponse<String> saveBlogComment(BlogComment blogComment) {
        BlogUser user=userMapper.selectUserByNameAndEmail(blogComment.getNickname(),blogComment.getEmail());//通过邮箱和昵称校验一下是不是管理员
        if(user==null){
            blogComment.setBlogger(false);
            BlogComment comment=commentMapper.selectByNickName(blogComment.getNickname());
            if(comment!=null){ //头像处理
                blogComment.setAvatar(comment.getAvatar());
            }else{
                blogComment.setAvatar(UserAvatarUtil.getUserAvatar());
            }
        }else{
            blogComment.setAvatar(user.getAvatar());
            blogComment.setBlogger(true);
        }
        int ans=commentMapper.insert(blogComment);
        if(ans!=0) return ServerResponse.createBySuccessMessage("评论发布成功！");
        return ServerResponse.createByErrorMessage("评论发布失败！");
    }

    /**
     * 需要将评论的多层树处理成两层的树结构
     */
    private List<PreEndBlogCommentVO> tempReplys=new ArrayList<>();//临时存储区
    @Override
    public List<PreEndBlogCommentVO> getBlogComment(Integer blogId) {
        //找出所有的顶级节点
        List<PreEndBlogCommentVO> CommentVOList=commentMapper.selectCommentByIdAndParentId(blogId,-1);
        for(PreEndBlogCommentVO commentVO:CommentVOList){ //遍历每个顶级节点，递归找出其所有子代节点
            recursively(commentVO);
            commentVO.setBlogCommentList(tempReplys);
            tempReplys=new ArrayList<>();
        }
        return CommentVOList;
    }

    /**
     * 递归找出所有子代节点
     * @param commentVO
     */
    private void recursively(PreEndBlogCommentVO commentVO){
        List<PreEndBlogCommentVO> preEndBlogComment=commentMapper.selectCommentByIdAndParentId(commentVO.getBlogId(),commentVO.getId());
        if(preEndBlogComment.size()>0){
            for(PreEndBlogCommentVO commentVO1:preEndBlogComment){
                tempReplys.add(commentVO1);
                if(commentMapper.selectCommentByIdAndParentId(commentVO1.getBlogId(),commentVO.getId()).size()>0){
                    recursively(commentVO1);
                }
            }
        }
    }
}
