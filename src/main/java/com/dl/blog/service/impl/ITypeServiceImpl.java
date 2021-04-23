package com.dl.blog.service.impl;

import com.dl.blog.common.ServerResponse;
import com.dl.blog.mapper.BlogTypeMapper;
import com.dl.blog.pojo.BlogType;
import com.dl.blog.service.ITypeService;
import com.dl.blog.vo.PreEndBlogTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ITypeService")
public class ITypeServiceImpl implements ITypeService {
    Logger logger= LoggerFactory.getLogger(ITypeServiceImpl.class);

    @Autowired
    private BlogTypeMapper typeMapper;

    @Override
    public ServerResponse<String> deleteTypeById(Integer id) {
        logger.info("删除博客分类数据Impl");
        int res=typeMapper.deleteByPrimaryKey(id);
        if(res>0){
            return ServerResponse.createBySuccessMessage("数据删除成功！");
        }
        return ServerResponse.createByErrorMessage("删除失败！");
    }

    @Override
    public List<BlogType> selectAllType() {
        List<BlogType> blogTypes=typeMapper.selectAllType();
        return blogTypes;
    }

    @Override
    public ServerResponse<BlogType> selectById(Integer id) {
        BlogType blogType=typeMapper.selectByPrimaryKey(id);
        if(blogType!=null){
            return ServerResponse.createBySuccess(blogType);
        }
        return ServerResponse.createByErrorMessage("查询无此数据！！！");
    }

    @Override
    public ServerResponse<String> insertType(BlogType blogType) {
        logger.info("进入添加博客类型数据接口！");
        BlogType blogType1=typeMapper.selectTypeName(blogType.getName());
        if(blogType1!=null) return ServerResponse.createByErrorMessage("类型已存在,请勿重新添加！");
        int res=typeMapper.insert(blogType);
        if(res!=0){
            return ServerResponse.createBySuccess("博客类型数据插入成功！");
        }
        return ServerResponse.createByErrorMessage("博客类型数据插入失败！");
    }

    @Override
    public ServerResponse<String> updateType(BlogType blogType) {
        logger.info("进入修改博客类型数据接口！");
        BlogType blogType1=typeMapper.selectTypeName(blogType.getName());
        if(blogType1!=null) return ServerResponse.createByErrorMessage("类型已存在,请重新修改！");
        int res=typeMapper.updateByPrimaryKey(blogType);
        if(res!=0){
            return ServerResponse.createBySuccessMessage("博客类型数据更新成功！");
        }
        return ServerResponse.createByErrorMessage("博客类型数据更新失败！");
    }

    @Override
    public ServerResponse<List<PreEndBlogTypeVO>> selectTypeToNum() {
        return ServerResponse.createBySuccess(typeMapper.selectTypeToCountList());
    }


}
