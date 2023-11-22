package com.ssafy.web.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.socket.model.ChatRoomDto;
import com.ssafy.web.socket.model.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService {

	private final ChatMapper chatMapper;
	
	@Autowired
	public ChatServiceImpl(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}

	@Override
	public int addUser(ChatRoomDto dto) {
		return chatMapper.addUser(dto);
	}

	@Override
	public int isValid(ChatRoomDto dto) {
		return chatMapper.isValid(dto);
	}

	@Override
	public int deleteRoom(ChatRoomDto dto) {
		return chatMapper.deleteRoom(dto);
	}

	@Override
	public int getNumOfUser(ChatRoomDto dto) {
		return chatMapper.getNumOfUser(dto);
	}

	@Override
	public String getUserRoomId(ChatRoomDto dto) {
		return chatMapper.getUserRoomId(dto);
	}

	@Override
	public int getPlanId(ChatRoomDto dto) {
		return chatMapper.getPlanId(dto);
	}


}
