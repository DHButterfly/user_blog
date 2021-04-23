package com.dl.blog.vo;

import com.dl.blog.pojo.BlogComment;
import com.dl.blog.pojo.BlogTag;
import com.dl.blog.pojo.BlogUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Alias("manageBlogDetailsVO")
@Data  //setter、getter、toString
@AllArgsConstructor
public class ManageBlogDetailsVO implements Serializable {
    private Integer id;
    private String title;
    private String firstPicture;
    private Boolean flag;
    private Integer views;
    private Boolean appreciation;
    private Boolean sharestatement;
    private Boolean commentabled;
    private Boolean published;
    private Boolean recommend;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private Integer typeId;
    private String content;
    private String description;
    private Integer userId;
    private BlogUser blogUser;
    private String tagIds;//博客标签
    private List<String> tags;
    private List<BlogComment> blogCommentList;//评论列表
}
