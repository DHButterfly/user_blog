package com.dl.blog.vo;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Alias("preEndBlogTagsVO")
@Data  //setter、getter、toString
@AllArgsConstructor
public class PreEndBlogTagsVO implements Serializable,Comparable<PreEndBlogTagsVO> {
    private Integer tid;
    private Integer tagBlogNum;
    private String tagName;

    //降序排列
    @Override
    public int compareTo(PreEndBlogTagsVO pebtv) {
        return pebtv.tagBlogNum.compareTo(this.tagBlogNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreEndBlogTagsVO that = (PreEndBlogTagsVO) o;
        return Objects.equals(tid, that.tid) &&
                Objects.equals(tagBlogNum, that.tagBlogNum) &&
                Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, tagBlogNum, tagName);
    }
}
