package com.ssafy.web.board.controller;

import java.util.List;

import com.ssafy.web.board.model.*;
import com.ssafy.web.board.model.mapper.BoardLikeMapper;
import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;
import com.ssafy.web.member.model.MemberDto;

import com.ssafy.web.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.ssafy.web.board.model.service.BoardService;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@PostMapping("/write")
	public ResponseEntity<MessageResponseDto> write(@RequestBody BoardDto boardDto, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession(false);
		MemberDto memberDto = (MemberDto) session.getAttribute("user_info");
		System.out.println(memberDto);
		boardDto.setUser_id(memberDto.getUser_id());

		boardService.writeArticle(boardDto);

		return ResponseEntity.ok(MessageResponseDto.builder().status(200).message("글작성성공").build());
	}

	@GetMapping("/list")
	public ResponseEntity<BoardListDto> list(@ModelAttribute PageDto page) throws Exception {
		System.out.println(page);
		List<BoardDto> list = boardService.listArticle(page);
		PageNavigation pageNavigation = boardService.makePageNavigation(page);

		return ResponseEntity.ok(BoardListDto.builder().page(pageNavigation).data(list).build());
	}

	@GetMapping("/view/{article_no}")
	public ResponseEntity<DetailAndCommentsDto> view(@PathVariable("article_no") int article_no,
			HttpServletRequest request) throws Exception {
		DetailAndCommentsDto response = null;
		HttpSession session = request.getSession(false);
		boolean editFalg = false;
		// 사용자 정보 가져온다.
		if (session != null) {
			MemberDto info = (MemberDto) session.getAttribute("user_info");
			String Author = boardService.getAuthor(article_no);

			response = boardService.getArticle(article_no, info.getUser_id());
			boardService.updateHit(article_no);
			if(Author != null  && Author.equals(info.getUser_id())) {
				editFalg = true;
			}
			
		} else {
			response = boardService.getArticle(article_no);
			boardService.updateHit(article_no);
		}
		return ResponseEntity.ok(DetailAndCommentsDto.builder().status(200).board(response.getBoard())
				.comments(response.getComments()).likes(response.getLikes()).edit(editFalg).build());
	}

	@PostMapping("/modify/{article_no}")
	public ResponseEntity<MessageResponseDto> modify(
			@PathVariable("article_no") int article_no,
			BoardDto boardDto, HttpServletRequest request) throws Exception {
		boolean success = false;
		HttpSession session = request.getSession(false);
		// 사용자 정보 가져온다.
		if (session != null) {
			MemberDto info = (MemberDto) session.getAttribute("user_info");
			String Author = boardService.getAuthor(article_no);
			if(Author != null  && Author.equals(info.getUser_id())) {
				boardService.modifyArticle(boardDto);
				success = true;
			}

		} 
		

		return ResponseEntity.ok(MessageResponseDto.builder().status(200).message("수정"+success).build());
	}

	@PostMapping("/delete")
	public ResponseEntity<MessageResponseDto> delete(@RequestBody BoardDto boardDto) throws Exception {

		boardService.deleteArticle(boardDto.getArticle_no());

		return ResponseEntity.ok(MessageResponseDto.builder().status(200).message("삭제성공").build());
	}

	@ApiOperation(value = "좋아요", notes = "좋아요 누르면 토글로 작동 mapper")
	@PostMapping("/like")
	public ResponseEntity<MessageResponseDto> like(LikeRequestStatusDto dto) throws Exception {
		System.out.println("좋아요");
		int i = boardService.boardLike(dto);
		if (i == 1) {
			System.out.println("뭐라도 함");
		} else {
			System.out.println("실패");
		}
		return ResponseEntity.ok(MessageResponseDto.builder().status(200).message("좋아요~").build());
	}

}
