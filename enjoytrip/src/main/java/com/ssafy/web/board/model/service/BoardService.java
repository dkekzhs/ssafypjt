package com.ssafy.web.board.model.service;

import com.ssafy.web.board.model.BoardDto;
import com.ssafy.web.board.model.DetailAndCommentsDto;
import com.ssafy.web.board.model.LikeRequestStatusDto;
import com.ssafy.web.board.model.PageDto;
import com.ssafy.web.util.PageNavigation;

import java.util.List;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(PageDto page) throws Exception;
	PageNavigation makePageNavigation(PageDto page) throws Exception;
	DetailAndCommentsDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;

	int boardLike(LikeRequestStatusDto dto);
}
