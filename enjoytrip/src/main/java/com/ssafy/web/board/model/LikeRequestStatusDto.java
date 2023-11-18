package com.ssafy.web.board.model;

import lombok.Builder;

public class LikeRequestStatusDto {
    private String user_id;
    private int article_no;
    private int like_status;

    @Builder
    public LikeRequestStatusDto(String user_id, int article_no) {
        this.user_id = user_id;
        this.article_no = article_no;
    }

    public int getArticle_no() {
        return article_no;
    }

    public void setArticle_no(int article_no) {
        this.article_no = article_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }
}
