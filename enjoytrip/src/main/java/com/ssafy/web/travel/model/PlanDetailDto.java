package com.ssafy.web.travel.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetailDto {

	private int plan_id;
	private int content_id;
	private int order;

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
		return "PlanDetailDto{" +
				"plan_id=" + plan_id +
				", content_id=" + content_id +
				", order=" + order +
				'}';
	}
}
