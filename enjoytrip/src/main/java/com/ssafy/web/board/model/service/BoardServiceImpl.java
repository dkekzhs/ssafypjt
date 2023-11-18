package com.ssafy.web.board.model.service;

import com.ssafy.web.board.model.*;
import com.ssafy.web.board.model.mapper.BoardLikeMapper;
import com.ssafy.web.board.model.mapper.BoardMapper;
import com.ssafy.web.comment.model.CommentDto;
import com.ssafy.web.comment.model.mapper.CommentMapper;
import com.ssafy.web.util.PageNavigation;
import com.ssafy.web.util.SizeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final CommentMapper commentMapper;
	private final BoardLikeMapper boardLikeMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper, CommentMapper commentMapper , BoardLikeMapper boardLikeMapper) {
		this.boardMapper = boardMapper;
		this.commentMapper = commentMapper;
		this.boardLikeMapper = boardLikeMapper;
	}

	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		System.out.println("글입력 전 dto : " + boardDto);
		boardMapper.writeArticle(boardDto);
		System.out.println("글입력 후 dto : " + boardDto);
	}

	@Override
	public List<BoardDto> listArticle(PageDto page) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = page.getKey();
		String sort = page.getSort();
		if("userid".equals(key))
			key = "b.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", page.getWord() == null ? "" : page.getWord());
		param.put("sort", sort == null ? "" : sort);
		int pgNo = (Integer)page.getPgno() == null ? 1 : page.getPgno();
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return boardMapper.listArticle(param);
	}
	
	@Override
	public PageNavigation makePageNavigation(PageDto page) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = page.getPgno();

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = page.getKey();
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", page.getWord() == null ? "" : page.getWord());
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);

		return pageNavigation;
	}

	@Override
	public DetailAndCommentsDto getArticle(int articleNo) throws Exception {
		BoardDto article = boardMapper.getArticle(articleNo);
		List<CommentDto> comments = commentMapper.getComments(articleNo);

		return DetailAndCommentsDto.builder()
				.board(article).comments(comments).build();
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		boardMapper.deleteArticle(articleNo);
		commentMapper.deleteCommentsByArticleNo(articleNo);
		}

	@Override
	public int boardLike(LikeRequestStatusDto dto) {

		return boardLikeMapper.callUpsertLikeStatus(dto);
	}

	@Override
	public DetailAndCommentsDto getArticle(int article_no, String user_id) throws  Exception{
		BoardDto article = boardMapper.getArticle(article_no);
		List<CommentDto> comments = commentMapper.getComments(article_no);
		LikeCountDto likes = boardLikeMapper.getArticleLikesAndStatus(
				LikeRequestStatusDto.builder()
						.article_no(article_no).user_id(user_id)
						.build()
		);
		System.out.println(likes);
		return DetailAndCommentsDto.builder()
				.board(article).comments(comments).likes(likes).build();
	}
}

