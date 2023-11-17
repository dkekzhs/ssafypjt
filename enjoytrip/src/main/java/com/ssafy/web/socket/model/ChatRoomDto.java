package com.ssafy.web.socket.model;

public class ChatRoomDto {

	private String room_id;
	private String user_id;

	public ChatRoomDto() {
		super();
	}

	public ChatRoomDto(String room_id, String user_id) {
		super();
		this.room_id = room_id;
		this.user_id = user_id;
	}



	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "ChatRoomDto [room_id=" + room_id + ", user_id=" + user_id + "]";
	}


}
