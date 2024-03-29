package com.ssafy.web.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ssafy.web.travel.model.PlanDto;
import com.ssafy.web.travel.model.PlanDto2;
import com.ssafy.web.travel.model.ReviewDto;
import com.ssafy.web.travel.model.TravelDto;
import com.ssafy.web.travel.model.mapper.TravelMapper;

@Service
public class TravelServiceImpl implements TravelService {

	private TravelMapper travelMapper;

	@Autowired
	public TravelServiceImpl(TravelMapper travelMapper) {
		super();
		this.travelMapper = travelMapper;
	}

	@Override
	public List<Map<String, String>> getGugunList(int sidoCode) throws Exception {
		return travelMapper.getGugunList(sidoCode);
	}

	@Override
	public List<Map<String, String>> getSidoList() throws Exception {
		return travelMapper.getSidoList();
	}

	@Override
	public List<Map<String, String>> getTypeList() throws Exception {
		return travelMapper.getTypeList();
	}

	@Override
	public List<TravelDto> searchByCode(int sidoCode, int gugunCode, int categoryCode) throws Exception {
		return travelMapper.searchByCode(sidoCode, gugunCode, categoryCode);
	}

	@Override
	public List<TravelDto> mapList(TravelDto travelDto) throws Exception {
		return travelMapper.mapList(travelDto);
	}

	@Override
	public List<TravelDto> searchByTitle(String title, int sidoCode, int gugunCode) throws Exception {
		return travelMapper.searchByTitle(title, sidoCode, gugunCode);
	}

	@Override
	public int create(PlanDto2 dto) {
		// 처리
		dto.setData();
		int i = travelMapper.CreatePlanBoardWithShare(dto);
		System.out.println(dto.getData());
		System.out.println(dto.getShare_user_id_list());
		return i;
	}

	@Override
	@Transactional
	public int CreateReview(ReviewDto dto) {
		boolean canReviewFlag = travelMapper.checkReviewExists(dto);
		if (!canReviewFlag) {
			travelMapper.CreateReview(dto);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public int deleteReview(ReviewDto dto) {
		boolean canReviewDelteFlag = travelMapper.checkReviewExists(dto);
		if (canReviewDelteFlag) {
			travelMapper.deleteReview(dto);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public List<ReviewDto> getReviewList(ReviewDto dto) {
		//when
		boolean canReview = !travelMapper.checkReviewExists(dto);
		List<ReviewDto> reviewList = travelMapper.getReviewList(dto);
		
		//여기서 리뷰dto말고 dto 하나 더 만들어서 처리 해야됨
		
		return reviewList;
	}

}
