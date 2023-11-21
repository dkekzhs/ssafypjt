package com.ssafy.web.travel.model;

import java.util.List;

import com.google.gson.Gson;
import lombok.Builder;

//flag 0== 전체공개  1 == 친구 공개 2 == 지정된 사람 공유 3== 나만보기
//CALL CreatePlanBoardWithShare('ssafy', 2, '["ssafy2","121212"]',"asdasd");
public class PlanSocketDto {
	private String user_id;
	private int plan_id,flag;
	private List<String> share_user_id_list;

	private String data;
	private String plan_name;

	public PlanSocketDto(){
		super();
	}

	@Builder
	public PlanSocketDto(String user_id, int flag, List<String> share_user_id_list, String plan_name) {
		this.user_id = user_id;
		this.flag = flag;
		this.share_user_id_list = share_user_id_list;
		this.plan_name = plan_name;
	}

	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setData() {
		this.data= new Gson().toJson(share_user_id_list);
	}
	public String getData() {
		return this.data;
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
				+ share_user_id_list +  ", data=" + data + ", plan_name=" + plan_name + "]";
	}


}