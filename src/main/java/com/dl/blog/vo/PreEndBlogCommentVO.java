package com.dl.blog.vo;

import com.dl.blog.pojo.BlogComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Alias("preEndBlogCommentVO")
@Data  //setter、getter、toString
@AllArgsConstructor
public class PreEndBlogCommentVO implements Serializable {
    private Integer id;

    private String nickname;

    private String email;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer blogId;

    private BlogComment parentComment; //这是因为需要展示父级评论的头像、nickname等等

    private String comment;

    private Boolean blogger;

    private List<PreEndBlogCommentVO> blogCommentList=new ArrayList<>();
}
