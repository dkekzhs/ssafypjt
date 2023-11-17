package com.ssafy.web.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.socket.mapper.ChatMapper;

@Service
public class ChatServiceImpl {

	private final ChatMapper chatMapper;
	
	@Autowired
	public ChatServiceImpl(ChatMapper chatMapper) {
		this.chatMapper = chatMapper;
	}
}
