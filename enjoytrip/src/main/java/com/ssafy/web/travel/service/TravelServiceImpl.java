package com.ssafy.web.travel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Map<String, String>>  getGugunList(int sidoCode) throws Exception {
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

	
}