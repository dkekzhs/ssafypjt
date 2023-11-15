package com.ssafy.web.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.web.member.model.User;

@Mapper
@Repository
public interface UserMapper {
	public String login(User user );	
	public void saveToken(User user);
}
