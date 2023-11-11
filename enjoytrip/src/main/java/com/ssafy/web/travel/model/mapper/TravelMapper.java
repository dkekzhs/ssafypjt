package com.ssafy.web.travel.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.web.travel.model.TravelDto;

@Mapper
public interface TravelMapper {

	List<Map<String, String>> getGugunList(int sidoCode) throws Exception;
	
	List<Map<String, String>> getSidoList() throws Exception;
	
	List<Map<String, String>> getTypeList() throws Exception;
	
	List<TravelDto> searchByCode(int sidoCode, int gugunCode, int categoryCode) throws Exception;

	List<TravelDto> mapList(TravelDto travelDto) throws Exception;

	List<TravelDto> searchByTitle(String title, int sidoCode, int gugunCode) throws Exception;

}