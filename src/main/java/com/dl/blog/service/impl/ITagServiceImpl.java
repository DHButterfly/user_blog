package com.dl.blog.service.impl;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.mapper.BlogTagMapper;
import com.dl.blog.mapper.BlogTagsUnionMapper;
import com.dl.blog.pojo.BlogTag;
import com.dl.blog.pojo.BlogTagsUnion;
import com.dl.blog.service.ITagService;
import com.dl.blog.util.StringToListUtil;
import com.dl.blog.vo.PreEndBlogTagsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ITagService")
public class ITagServiceImpl implements ITagService {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogTagMapper tagMapper;
    @Autowired
    private BlogTagsUnionMapper tagsUnionMapper;
    @Override
    public List<BlogTag> selectBlogTags() {
        return tagMapper.selectAllTag();
    }

    @Override
    public ServerResponse<String> deleteTagById(Integer id) {
        logger.info("删除博客标签数据Impl");
        int res=tagMapper.deleteByPrimaryKey(id);
        if(res>0){
            return ServerResponse.createBySuccessMessage("标签数据删除成功！");
        }
        return ServerResponse.createByErrorMessage("标签数据删除失败！");
    }

    @Override
    public ServerResponse<String> saveBlogTag(BlogTag blogTag) {
        BlogTag blogTag1=tagMapper.selectByTagName(blogTag.getName());
        if(blogTag1!=null){
            return ServerResponse.createByErrorMessage("该标签已经存在，请勿重复添加！");
        }
        if(blogTag.getId()==null){
            int ans=tagMapper.insertSelective(blogTag);
            if(ans!=0){
                return ServerResponse.createBySuccess("博客标签数据新增成功！");
            }
            return ServerResponse.createBySuccessMessage("博客标签数据新增失败！");
        }else{
            int ans=tagMapper.updateByPrimaryKeySelective(blogTag);
            if(ans!=0){
                return ServerResponse.createBySuccess("博客标签数据更新成功！");
            }
            return ServerResponse.createBySuccessMessage("博客标签数据更新失败！");
        }
    }

    @Override
    public ServerResponse<List<PreEndBlogTagsVO>> selectPreTagsNum() {
        //先查出所有中间表的内容
        List<BlogTagsUnion> blogTagsUnions=tagsUnionMapper.selectAllTagsUnion();
        List<PreEndBlogTagsVO> preEndBlogTagsVOList=new ArrayList<>();
        Map<Integer,Integer> tagToBlogMap=new HashMap<>();//tid,blogNum
        for(BlogTagsUnion blogTagsUnion:blogTagsUnions){//枚举所有的bid，tid
            String tagIds=blogTagsUnion.getTid();
            List<String> tagList= StringToListUtil.convertToList(tagIds); //将标签String转为List
            for(String tagId:tagList){ //该博客下枚举所有的标签
                Integer ItagId=Integer.parseInt(tagId);
                if(tagToBlogMap.get(ItagId)==null){
                    tagToBlogMap.put(ItagId,1);
                }else{
                    tagToBlogMap.put(ItagId,tagToBlogMap.get(ItagId)+1);
                }
            }
        }
        for(Integer tagId:tagToBlogMap.keySet()){ //处理成tagId-Num形式
            PreEndBlogTagsVO preEndBlogTagsVO=new PreEndBlogTagsVO();
            String tagName=tagMapper.selectTagNameById(tagId);
            preEndBlogTagsVO.setTid(tagId);
            preEndBlogTagsVO.setTagBlogNum(tagToBlogMap.get(tagId));
            preEndBlogTagsVO.setTagName(tagName);
            preEndBlogTagsVOList.add(preEndBlogTagsVO);
        }
        Collections.sort(preEndBlogTagsVOList);
        return ServerResponse.createBySuccess(preEndBlogTagsVOList);
    }


}
