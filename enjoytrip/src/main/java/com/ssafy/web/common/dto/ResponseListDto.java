package com.ssafy.web.common.dto;

import lombok.Builder;

public class ResponseListDto <T>{
    private int status;
    private T list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Builder
    public ResponseListDto(int status, T list) {
        this.status = status;
        this.list = list;
    }

    public T getData() {
        return list;
    }

    public void setData(T list) {
        this.list = list;
    }
}
