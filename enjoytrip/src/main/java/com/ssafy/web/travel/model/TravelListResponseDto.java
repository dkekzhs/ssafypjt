package com.ssafy.web.travel.model;


import lombok.Builder;

public class TravelListResponseDto <T> {
    private int status;
    private T data;

    @Builder
    public TravelListResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
