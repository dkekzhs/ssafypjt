package com.ssafy.web.travel.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.web.socket.model.getMyPlanDto;
import com.ssafy.web.travel.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelMapper {

	List<Map<String, String>> getGugunList(int sidoCode) throws Exception;
	
	List<Map<String, String>> getSidoList() throws Exception;
	
	List<Map<String, String>> getTypeList() throws Exception;
	
	List<TravelDto> searchByCode(int sidoCode, int gugunCode, int categoryCode) throws Exception;

	List<TravelDto> mapList(TravelDto travelDto) throws Exception;

	List<TravelDto> searchByTitle(String title, int sidoCode, int gugunCode) throws Exception;
	
	int CreatePlanBoardWithShare(PlanSocketDto dto);
	int sharePlan(PlanDto dto);

	int CreateReview(ReviewDto dto);
	
	boolean checkReviewExists(ReviewDto dto);
	int deleteReview(ReviewDto dto);

	List<ReviewDto> getReviewList(ReviewDto dto);

	List<SocketPlanDto> getAttractionInfoByPlanId(int plan_id);
	
	int addPlan(PlanDetailDto dto);

	SocketPlanDto getPlanOne(int content_id);

	int deletePlan(PlanDetailDto dto);

	int updatePlan(PlanDetailDto dto);

	List<getMyPlanDto> getMyPlan(String user_id);
}