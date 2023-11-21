package com.ssafy.web.travel.model;

import java.util.List;

public class TestDto {

	private String user_id;
	private int share_flag;
	
	private List<String> shared_user_id_list;
	private String share_user_id_json;
	
	
	public TestDto() {
		super();
	}
	
	
	public TestDto(String user_id, int share_flag, List<String> shared_user_id_list, String share_user_id_json) {
		super();
		this.user_id = user_id;
		this.share_flag = share_flag;
		this.shared_user_id_list = shared_user_id_list;
		this.share_user_id_json = share_user_id_json;
	}


	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getShare_flag() {
		return share_flag;
	}
	public void setShare_flag(int share_flag) {
		this.share_flag = share_flag;
	}


	public List<String> getShared_user_id_list() {
		return shared_user_id_list;
	}


	public void setShared_user_id_list(List<String> shared_user_id_list) {
		this.shared_user_id_list = shared_user_id_list;
	}


	public String getShare_user_id_json() {
		return share_user_id_json;
	}


	public void setShare_user_id_json(String share_user_id_json) {
		this.share_user_id_json = share_user_id_json;
	}


	@Override
	public String toString() {
		return "TestDto [user_id=" + user_id + ", share_flag=" + share_flag + ", shared_user_id_list="
				+ shared_user_id_list + ", share_user_id_json=" + share_user_id_json + "]";
	}



	
	
	
}
