package com.ssafy.web.board.model;

public class PageDto {

    private int pgno, spp;
    private String key;
    private String word;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private String sort;



    public int getPgno() {
        return pgno;
    }

    public void setPgno(int pgno) {
        this.pgno = pgno;
    }

    public int getSpp() {
        return spp;
    }

    public void setSpp(int spp) {
        this.spp = spp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
