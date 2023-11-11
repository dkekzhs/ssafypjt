package com.ssafy.web.board.model;

import com.ssafy.web.util.PageNavigation;
import lombok.Builder;

public class BoardListDto <T>{
    private int dto;
    private T data;
    private PageNavigation page;

    @Builder
    public BoardListDto(int dto, T data, PageNavigation page) {
        this.dto = dto;
        this.data = data;
        this.page = page;
    }

    public int getDto() {
        return dto;
    }

    public void setDto(int dto) {
        this.dto = dto;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageNavigation getPage() {
        return page;
    }

    public void setPage(PageNavigation page) {
        this.page = page;
    }
}
