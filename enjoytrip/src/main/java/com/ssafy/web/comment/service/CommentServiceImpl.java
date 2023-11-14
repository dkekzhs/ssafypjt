package com.ssafy.web.comment.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.comment.model.CommentDto;
import com.ssafy.web.comment.model.mapper.CommentMapper;


@Service
public class CommentServiceImpl implements CommentService{
	private final CommentMapper cm;
	
	
	@Autowired
	public CommentServiceImpl(CommentMapper cm) {
		super();
		this.cm = cm;
	}


	@Override
	public int add(CommentDto dto) {
		try {
			int cnt = cm.add(dto);
			return cnt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<CommentDto> getComments(int  article_no) {
		return cm.getComments(article_no);
	}

	@Override
	public int deleteComment(int comment_id) {
		return cm.deleteComment(comment_id);
	}

	@Override
	public int deleteCommentsByArticleNo(int article_no) {
		return cm.deleteCommentsByArticleNo(article_no);
	}

}
