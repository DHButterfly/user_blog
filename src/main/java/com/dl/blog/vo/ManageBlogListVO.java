package com.dl.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class ManageBlogListVO implements Serializable {
    private Integer id;
    private String title;
    private Integer typeId;
    private Boolean recommend;
    private Boolean published;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Override
    public String toString() {
        return "BlogListVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", typeId=" + typeId +
                ", recommend=" + recommend +
                ", published=" + published +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ManageBlogListVO() {
    }

    public ManageBlogListVO(Integer id, String title, Integer typeId, Boolean recommend, Boolean published, Date updateTime) {
        this.id = id;
        this.title = title;
        this.typeId = typeId;
        this.recommend = recommend;
        this.published = published;
        this.updateTime = updateTime;
    }
}
