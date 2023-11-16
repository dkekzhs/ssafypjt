package com.ssafy.web.board.model;

import com.ssafy.web.comment.model.CommentDto;
import lombok.Builder;

import java.util.List;

public class DetailAndCommentsDto {
    private int status;
    private BoardDto board;
    private List<CommentDto> comments;


    @Builder
    public DetailAndCommentsDto(BoardDto board, List<CommentDto> comments, int status) {
        this.status = status;
        this.board = board;
        this.comments = comments;
    }
    @Builder
    public DetailAndCommentsDto(BoardDto board, List<CommentDto> comments) {

        this.board = board;
        this.comments = comments;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BoardDto getBoard() {
        return board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
