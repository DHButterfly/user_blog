package com.dl.blog.pojo;

public class BlogTagsUnion {
    private Integer bid;

    private String tid;

    public BlogTagsUnion(Integer bid, String tid) {
        this.bid = bid;
        this.tid = tid;
    }

    public BlogTagsUnion() {
        super();
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bid=").append(bid);
        sb.append(", tid=").append(tid);
        sb.append("]");
        return sb.toString();
    }
}