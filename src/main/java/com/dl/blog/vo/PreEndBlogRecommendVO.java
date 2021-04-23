package com.dl.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 最新推荐文章
 */

@NoArgsConstructor
@Alias("preEndBlogRecommendVO")
@Data  //setter、getter、toString
@AllArgsConstructor
public class PreEndBlogRecommendVO implements Serializable {
    private Integer id;
    private String title;
}
