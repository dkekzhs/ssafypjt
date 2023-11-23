package com.ssafy.web.travel.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetailDto {

	private int plan_id;
	private int contentId;
	private int order;

	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}


	@Override
	public String toString() {
		return "PlanDetailDto{" +
				"plan_id=" + plan_id +
				", contentId=" + contentId +
				", order=" + order +
				'}';
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
