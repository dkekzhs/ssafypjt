package com.ssafy.web.socket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {

	private String room_id;
	private String user_id;
	private int plan_id;


	public int getPlan_id() {
		return plan_id;
	}




	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
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
