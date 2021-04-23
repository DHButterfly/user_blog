package com.dl.blog.pojo;

import java.util.Date;

public class BlogComment {
    private Integer id;

    private String nickname;

    private String email;

    private String avatar;

    private Date createTime;

    private Integer blogId;

    private Integer parentCommentId;

    private Boolean blogger;

    private String comment;

    public BlogComment(Integer id, String nickname, String email, String avatar, Date createTime, Integer blogId, Integer parentCommentId, Boolean blogger, String comment) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.createTime = createTime;
        this.blogId = blogId;
        this.parentCommentId = parentCommentId;
        this.blogger = blogger;
        this.comment = comment;
    }

    public BlogComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Boolean getBlogger() {
        return blogger;
    }

    public void setBlogger(Boolean blogger) {
        this.blogger = blogger;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nickname=").append(nickname);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", createTime=").append(createTime);
        sb.append(", blogId=").append(blogId);
        sb.append(", parentCommentId=").append(parentCommentId);
        sb.append(", blogger=").append(blogger);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}