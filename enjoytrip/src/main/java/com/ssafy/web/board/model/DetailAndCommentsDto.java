package com.ssafy.web.board.model;

import com.ssafy.web.comment.model.CommentDto;
import lombok.Builder;

import java.util.List;

public class DetailAndCommentsDto {
    private int status;
    private BoardDto board;
    private List<CommentDto> comments;
    
    private LikeCountDto likes;
    private boolean edit;
    private String user_id;

    
    
    
    @Builder
    public DetailAndCommentsDto(int status, BoardDto board, List<CommentDto> comments, LikeCountDto likes, boolean edit,
			String user_id) {
		super();
		this.status = status;
		this.board = board;
		this.comments = comments;
		this.likes = likes;
		this.edit = edit;
		this.user_id = user_id;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public boolean isEdit() {
		return edit;
	}


	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	@Builder
	public DetailAndCommentsDto(int status, BoardDto board, List<CommentDto> comments, LikeCountDto likes,
			boolean edit) {
		super();
		this.status = status;
		this.board = board;
		this.comments = comments;
		this.likes = likes;
		this.edit = edit;
	}


	@Builder
    public DetailAndCommentsDto(int status, BoardDto board, List<CommentDto> comments, LikeCountDto likes) {
        this.status = status;
        this.board = board;
        this.comments = comments;
        this.likes = likes;
    }


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
    public LikeCountDto getLikes() {
        return likes;
    }

    public void setLikes(LikeCountDto likes) {
        this.likes = likes;
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
