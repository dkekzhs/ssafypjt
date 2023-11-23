package com.ssafy.web.socket.service;

import com.ssafy.web.socket.model.ChatRoomDto;

public interface ChatService {

	// user id와 chat room id가 넘어오면 chat 테이블에 추가하기
	public int addUser(ChatRoomDto dto);
	
	// user가 들어가려는 chat room이 유효한지 판별
	public int isValid(ChatRoomDto dto);
	
	// chat room이 사라지면 해당 chat room의 user id 모두 삭제
	public int deleteRoom(ChatRoomDto dto);
	
	// 채팅방에 남아있는 유저 수 반환
	public int getNumOfUser(ChatRoomDto dto);
	
	// user id에 해당하는 방 이름 가져오기
	public String getUserRoomId(ChatRoomDto dto);

	// plan_id  가져오기
	public int getPlanId(ChatRoomDto dto);

	// 방장의 임시 공유 plan_id 갱신
	public void updateUser(ChatRoomDto dto);
}
