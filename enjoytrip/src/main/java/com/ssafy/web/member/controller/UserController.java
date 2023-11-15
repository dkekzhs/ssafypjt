package com.ssafy.web.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.member.model.User;
import com.ssafy.web.member.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService us;
	
	@PostMapping("/jwtlogin")
	public ResponseEntity<String[]> jwtlogin(@RequestBody User user) {
		System.out.println(user.getId() + " " + user.getPw());
		
		String[] login = us.login(user);
		return ResponseEntity.ok(login);
		
	}
	
}
