package com.ssafy.web.board.controller;

import java.util.List;

import com.ssafy.web.board.model.BoardDto;
import com.ssafy.web.board.model.BoardListDto;
import com.ssafy.web.board.model.PageDto;
import com.ssafy.web.common.dto.ResponseDto;
import com.ssafy.web.member.model.MemberDto;

import com.ssafy.web.util.PageNavigation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.ssafy.web.board.model.service.BoardService;

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
	public ResponseEntity<ResponseDto> write(@RequestBody BoardDto boardDto, HttpSession session) throws Exception {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		boardDto.setUser_id(memberDto.getUser_id());

		boardService.writeArticle(boardDto);

		return ResponseEntity.ok(ResponseDto.builder().status(200).data("글작성성공").build());
	}

	@GetMapping("/list")
	public ResponseEntity<BoardListDto> list(@RequestBody PageDto page) throws Exception {
		List<BoardDto> list = boardService.listArticle(page);
		PageNavigation pageNavigation = boardService.makePageNavigation(page);


		return ResponseEntity.ok(BoardListDto.builder()
				.page(pageNavigation).data(list).build());
	}

	@GetMapping("/view")
	public ResponseEntity<ResponseDto> view(@RequestParam("article_no") int article_no)
			throws Exception {
		BoardDto boardDto = boardService.getArticle(article_no);
		boardService.updateHit(article_no);

		return ResponseEntity.ok(ResponseDto.builder()
						.status(200).data(boardDto)
				.build());
	}


	@PostMapping("/modify")
	public ResponseEntity<ResponseDto> modify(BoardDto boardDto) throws Exception {
		boardService.modifyArticle(boardDto);

		return ResponseEntity.ok(ResponseDto.builder()
				.status(200).data("수정성공").build());
	}

	@PostMapping("/delete")
	public  ResponseEntity<ResponseDto> delete(@RequestBody BoardDto boardDto) throws Exception {

		boardService.deleteArticle(boardDto.getArticle_no());

		return ResponseEntity.ok(ResponseDto.builder()
				.status(200).data("삭제성공").build());
	}


	


}
