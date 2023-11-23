package com.ssafy.web.travel.controller;

import com.ssafy.web.common.dto.ResponseListDto;
import com.ssafy.web.common.exception.AuthException;
import com.ssafy.web.member.model.MemberDto;
import com.ssafy.web.socket.model.getMyPlanDto;
import com.ssafy.web.travel.model.*;
import com.ssafy.web.travel.model.mapper.TravelMapper;
import com.ssafy.web.travel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
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
	public ResponseEntity<?> createPlan(@RequestBody PlanSocketDto dto){
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

	@GetMapping("/getMyPlan")
	public ResponseEntity<?> getMyPlan(HttpServletRequest request) throws AuthException {
		HttpSession session = request.getSession(false);
		if(session != null){
			MemberDto user_info = (MemberDto) session.getAttribute("user_info");
			List<getMyPlanDto> dto = travelService.getMyPlan(user_info.getUser_id());
			return ResponseEntity.ok(ResponseListDto.builder().list(dto).build());
		}
		throw new AuthException("여행계획 리스트가져오기 오류");
	}
	
	@PostMapping("/getPlanDetail")
	public ResponseEntity<?> gePlanDetail(HttpServletRequest request ,@RequestBody getMyPlanDto dto) throws  AuthException {
		HttpSession session = request.getSession(false);
		if(session == null) throw new AuthException("인증 오류");

		List<SocketPlanDto> data = travelService.getAttractionInfoByPlanId(dto.getPlan_id());
		return ResponseEntity.ok(ResponseListDto.builder().list(data).build());

	}
	
}
