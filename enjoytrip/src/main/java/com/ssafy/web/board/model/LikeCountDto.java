package com.ssafy.web.board.model;

import lombok.Builder;

public class LikeCountDto {
    private int dislike_count,like_count,like_status;

	public int getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(int dislike_count) {
        this.dislike_count = dislike_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    @Override
    public String toString() {
        return "LikeCountDto{" +
                "dislike_count=" + dislike_count +
                ", like_count=" + like_count +
                ", like_status=" + like_status +
                '}';
    }
}
