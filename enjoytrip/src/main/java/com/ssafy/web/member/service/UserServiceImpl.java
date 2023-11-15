package com.ssafy.web.member.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.member.model.User;
import com.ssafy.web.member.model.mapper.MemberMapper;
import com.ssafy.web.member.model.mapper.UserMapper;
import com.ssafy.web.util.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper um ;
	@Override
	public String[] login(User user) {
		//jwt init 
		String salt = UUID.randomUUID().toString();
		
		String access_token = JwtTokenProvider.createAccessToken(user, salt);
		String refresh_token = JwtTokenProvider.createRefreshToken(user.getId(), salt);
		
		user.setRefresh_token(refresh_token);
		user.setAccess_token(access_token);
		user.setSalt(salt);
		um.saveToken(user);
		return new String [] {access_token,refresh_token};
	}

	
	
	
}
