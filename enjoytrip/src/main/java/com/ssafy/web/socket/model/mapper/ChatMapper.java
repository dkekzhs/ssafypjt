package com.ssafy.web.socket.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.web.socket.model.ChatRoomDto;

@Repository
@Mapper
public interface ChatMapper {

	public int addUser(ChatRoomDto dto);

	public int isValid(ChatRoomDto dto);
	
	public int deleteRoom(ChatRoomDto dto);
	
	public int getNumOfUser(ChatRoomDto dto);

	public String getUserRoomId(ChatRoomDto dto);

	int getPlanId(ChatRoomDto dto);
}
