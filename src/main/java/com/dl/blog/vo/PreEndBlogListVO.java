package com.dl.blog.vo;

import com.dl.blog.pojo.BlogType;
import com.dl.blog.pojo.BlogUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Alias("preEndBlogListVO")
@Data  //setter、getter、toString
@AllArgsConstructor
public class PreEndBlogListVO implements Serializable , Comparator<PreEndBlogListVO> {
    private Integer id;
    private String title;
    private String firstPicture;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private Integer userId;
    private Integer typeId;
    private BlogUser blogUser;
    private BlogType blogType;
    private String description;
    private Boolean flag;
    private List<String> tags;

    @Override
    public int compare(PreEndBlogListVO o1, PreEndBlogListVO o2) {  //倒序
         if(o1.id<o2.id) return 1;
         else if(o1.id>o2.id) return -1;
         return 0;
    }
}
