package com.dl.blog.pojo;

import java.util.Date;

public class Blog {
    private Integer id;

    private String title;

    private String firstPicture;

    private Integer views;

    private Boolean flag;

    private Boolean appreciation;

    private Boolean sharestatement;

    private Boolean commentabled;

    private Boolean published;

    private Boolean recommend;

    private Date createTime;

    private Date updateTime;

    private Integer typeId;

    private Integer userId;

    private String description;

    private String content;

    public Blog(Integer id, String title, String firstPicture, Integer views, Boolean flag, Boolean appreciation, Boolean sharestatement, Boolean commentabled, Boolean published, Boolean recommend, Date createTime, Date updateTime, Integer typeId, Integer userId, String description, String content) {
        this.id = id;
        this.title = title;
        this.firstPicture = firstPicture;
        this.views = views;
        this.flag = flag;
        this.appreciation = appreciation;
        this.sharestatement = sharestatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.typeId = typeId;
        this.userId = userId;
        this.description = description;
        this.content = content;
    }

    public Blog() {
        super();
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
        this.title = title == null ? null : title.trim();
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture == null ? null : firstPicture.trim();
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getSharestatement() {
        return sharestatement;
    }

    public void setSharestatement(Boolean sharestatement) {
        this.sharestatement = sharestatement;
    }

    public Boolean getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Boolean commentabled) {
        this.commentabled = commentabled;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", firstPicture=").append(firstPicture);
        sb.append(", views=").append(views);
        sb.append(", flag=").append(flag);
        sb.append(", appreciation=").append(appreciation);
        sb.append(", sharestatement=").append(sharestatement);
        sb.append(", commentabled=").append(commentabled);
        sb.append(", published=").append(published);
        sb.append(", recommend=").append(recommend);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", typeId=").append(typeId);
        sb.append(", userId=").append(userId);
        sb.append(", description=").append(description);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}