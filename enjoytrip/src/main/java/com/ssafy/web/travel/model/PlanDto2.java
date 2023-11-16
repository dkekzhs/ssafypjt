package com.ssafy.web.travel.model;

import java.util.List;

//flag 0== 전체공개  1 == 친구 공개 2 == 지정된 사람 공유 3== 나만보기
public class PlanDto2 {
	private String user_id;
	private int plan_id,flag;
	private List<String> share_user_id_list;
	private String temp;
	
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public List<String> getShare_user_id_list() {
		return share_user_id_list;
	}
	public void setShare_user_id_list(List<String> share_user_id_list) {
		this.share_user_id_list = share_user_id_list;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "PlanDto2 [user_id=" + user_id + ", plan_id=" + plan_id + ", flag=" + flag + ", share_user_id_list="
				+ share_user_id_list + ", temp=" + temp + "]";
	}

}
