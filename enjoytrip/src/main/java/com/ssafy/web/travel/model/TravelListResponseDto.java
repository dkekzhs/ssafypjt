package com.ssafy.web.travel.model;


import lombok.Builder;

public class TravelListResponseDto <T> {
    private int status;
    private T list;

    @Builder
    public TravelListResponseDto(int status, T list) {
        this.status = status;
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public T getList() {
		return list;
	}

	public void setList(T list) {
		this.list = list;
	}

}
