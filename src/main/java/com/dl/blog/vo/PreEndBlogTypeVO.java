package com.dl.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@NoArgsConstructor
@Alias("preEndBlogTypeVo")
@Data  //setter、getter、toString
@AllArgsConstructor
public class PreEndBlogTypeVO implements Serializable {
    private Integer typeId;
    private String typeName;
    private Integer typeBlogNum;
}
