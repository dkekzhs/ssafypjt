package com.ssafy.web.member.controller;

import com.ssafy.web.common.dto.MessageResponseDto;
import com.ssafy.web.common.dto.ResponseListDto;
import com.ssafy.web.common.exception.AuthException;
import com.ssafy.web.member.model.LoginResponseDto;
import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.RsaDto;
import com.ssafy.web.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class MemberController {
	@Autowired
	private MemberService ms;
	
	@PostMapping("/getPublicKey")
	public ResponseEntity<RsaDto> getPublicKey(HttpServletRequest request){

		RsaDto dto = ms.getPublicKey(request.getRemoteAddr());
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertUser(HttpServletRequest request, @RequestBody MemberDto dto){
		int insert = ms.insert(dto,request.getRemoteAddr());
		if(insert == 1) {
			return ResponseEntity.ok("성공");
		}
		else {
			return ResponseEntity.ok("실패");
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody MemberDto dto, HttpServletRequest request) {
		System.out.println(dto);

		MemberDto login = ms.loginMember(dto,request.getRemoteAddr());
		System.out.println(login);
		if(login != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user_info",login);
			
			return ResponseEntity.ok(LoginResponseDto.builder()
					.status(200)
					.message("로그인 성공")
					.user_name(login.getUser_name())
					.build());
		}
		return ResponseEntity.badRequest().body(LoginResponseDto.builder().message("로그인 실패").build());
	}
	
	@PostMapping("/logout")
	public ResponseEntity<MessageResponseDto> login(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}		
		
		return ResponseEntity.ok(
				MessageResponseDto.builder().status(200).message("로그아웃 성공").build());
		
	}
	@PostMapping("/search")
	public ResponseEntity<ResponseListDto> search(@RequestBody Map<String,String> map, HttpServletRequest request) throws AuthException {
		HttpSession session = request.getSession(false);
		if(session != null){
			MemberDto memberDto = (MemberDto) session.getAttribute("user_info");
			System.out.println(memberDto.getUser_id() + "  " + map.get("search"));
			List<String> users = ms.getUserList(memberDto.getUser_id(),map.get("search"));
			return ResponseEntity.ok(ResponseListDto.builder().list(users).build());
		}
		throw new AuthException("로그인해주세요");
	}

}
