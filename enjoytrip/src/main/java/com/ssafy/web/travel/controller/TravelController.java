package com.ssafy.web.travel.controller;

import com.ssafy.web.travel.model.PlanDto;
import com.ssafy.web.travel.model.PlanDto2;
import com.ssafy.web.travel.model.ReviewDto;
import com.ssafy.web.travel.model.TravelDto;
import com.ssafy.web.travel.model.TravelListResponseDto;
import com.ssafy.web.travel.model.mapper.TravelMapper;
import com.ssafy.web.travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel")
public class TravelController {

	private TravelService travelService;

	@Autowired
	public TravelController(TravelService travelService, TravelMapper travelMapper) {
		super();
		this.travelService = travelService;
	}



	@PostMapping("/search/getGugunList")
	public ResponseEntity<TravelListResponseDto> getGugunList(@RequestBody TravelDto requestBody)
			throws NumberFormatException, Exception {

		List<Map<String,String>> ret = travelService.getGugunList(requestBody.getSidoCode());
		System.out.println(ret);

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).list(ret).build());
	}

	@PostMapping("/search/getSidoList")
	public ResponseEntity<TravelListResponseDto> getSidoList() throws Exception {
		System.out.println("getSidoList()");

		List<Map<String,String>> ret = travelService.getSidoList();

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).list(ret).build());
	}

	@PostMapping("/search/getTypeList")
	public ResponseEntity<TravelListResponseDto> getTypeList() throws Exception {
		System.out.println("getTypeList");

		List<Map<String,String>> ret = travelService.getTypeList();

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).list(ret).build());
	}

	@PostMapping("/search/travel")
	public ResponseEntity<TravelListResponseDto> searchByGugunCode(@RequestBody TravelDto req)
			throws NumberFormatException, Exception {
		System.out.println(req.getSidoCode());
		System.out.println(req.getGugunCode());
		System.out.println(req.getContentTypeId());
		List<TravelDto> ret = travelService.searchByCode(req.getSidoCode(),req.getGugunCode(), req.getContentTypeId());
		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).list(ret).build());
	}
	
	@PostMapping("/plan/create")
	public ResponseEntity<?> createPlan(@RequestBody PlanDto2 dto){
		int create = travelService.create(dto);
		return ResponseEntity.ok(create);
	}
	
	@PostMapping("/review/create")
	public ResponseEntity<?> createReview(@RequestBody ReviewDto dto){
		int create = travelService.CreateReview(dto);
		if(create == 1) {
			return ResponseEntity.ok("1");
		}else {
			return ResponseEntity.ok("0");
		}
		
	}
	@PostMapping("/review/delte")
	public ResponseEntity<?> deleteReview(@RequestBody ReviewDto dto){
		int create = travelService.deleteReview(dto);
		if(create == 1) {
			return ResponseEntity.ok("1");
		}else {
			return ResponseEntity.ok("0");
		}
		
	}
	
	@PostMapping("/review/list")
	public ResponseEntity<?> reviewList(@RequestBody ReviewDto dto){
		List<ReviewDto> create = travelService.getReviewList(dto);
		return ResponseEntity.ok(create);
		
	}
	
	
	
}
