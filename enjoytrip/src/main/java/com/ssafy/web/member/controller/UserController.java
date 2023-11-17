package com.ssafy.web.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.User;
import com.ssafy.web.member.service.UserService;
import com.ssafy.web.user.model.service.OAuthService;

@RestController
@RequestMapping("/user2")
public class UserController {

	@Autowired
	private UserService us;

	@Autowired
	OAuthService oAuthService;

	@PostMapping("/jwtlogin")
	public ResponseEntity<String[]> jwtlogin(@RequestBody User user) {
		System.out.println(user.getId() + " " + user.getPw());

		String[] login = us.login(user);
		return ResponseEntity.ok(login);

	}

	@GetMapping("/kakaologin")
	public Map<String, Object> kakaologin(@RequestParam String code) {
		System.out.println("kakao login");
		System.out.println("code = " + code);
		String access_Token = oAuthService.getKaKaoAccessToken(code);
		System.out.println("access toekn " + access_Token);
		String email = oAuthService.createKakaoUser(access_Token);
		if (email != null) {
			System.out.println("UserController : " + email);
//			HttpSession session = request.getSession();
//			session.setAttribute("email", email);
//			Cookie c = new Cookie("id", email);
//			c.setHttpOnly(true);
//			response.addCookie(c);

			// jwt 발급
//			User user = new User();
//			user.setId(email);
//			String[] tokens = us.login(user);
			Map<String, Object> resMap = new HashMap<>();
			resMap.put("tokens", email);
			return resMap;

		}

		return null;
	}

	private void jwtCreate() {
	}

}
