package com.ssafy.web.common.dto;

import lombok.Builder;

public class ResponseDto <T>{
    private int status;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Builder
    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
