package com.ssafy.web.travel.model;

public class PlanDetailDto {

	private int plan_id;
	private int content_id;
	
	
	public PlanDetailDto() {
		super();
	}
	
	public PlanDetailDto(int plan_id, int content_id) {
		super();
		this.plan_id = plan_id;
		this.content_id = content_id;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	
	@Override
	public String toString() {
		return "PlanDetailDto [plan_id=" + plan_id + ", content_id=" + content_id + "]";
	}
}
