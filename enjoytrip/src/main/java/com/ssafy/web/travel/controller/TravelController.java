package com.ssafy.web.travel.controller;

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
				.status(200).data(ret).build());
	}

	@PostMapping("/search/getSidoList")
	public ResponseEntity<TravelListResponseDto> getSidoList() throws Exception {
		System.out.println("getSidoList()");

		List<Map<String,String>> ret = travelService.getSidoList();

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).data(ret).build());
	}

	@PostMapping("/search/getTypeList")
	public ResponseEntity<TravelListResponseDto> getTypeList() throws Exception {
		System.out.println("getTypeList");

		List<Map<String,String>> ret = travelService.getTypeList();

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).data(ret).build());
	}

	@PostMapping("/search/gugunCode")
	public ResponseEntity<TravelListResponseDto> searchByGugunCode(@RequestBody TravelDto requestBody)
			throws NumberFormatException, Exception {

		int gugunCode = requestBody.getGugunCode();
		List<TravelDto> ret = travelService.searchByCode(0, gugunCode, 0);

		return ResponseEntity.ok(TravelListResponseDto.builder()
				.status(200).data(ret).build());
	}
}
