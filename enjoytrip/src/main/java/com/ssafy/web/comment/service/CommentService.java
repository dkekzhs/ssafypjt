package com.ssafy.web.comment.service;

import com.ssafy.web.comment.model.CommentDto;

import java.util.List;

public interface CommentService {

	int add(CommentDto dto);

    List<CommentDto> getComments(int article_no);

    int deleteComment(int comment_id);

    int deleteCommentsByArticleNo(int article_no);
}
