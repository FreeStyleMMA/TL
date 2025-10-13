package com.tl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;

public interface PerformanceDBMapper {
	public void addPerformance(PerformanceInfoDto info);	// db에 공연 정보 추가
	public void addTicket(List<TicketDto> ticket);			// db에 예매처 정보 추가
	public int checkPerformance(PerformanceInfoDto info);	// 일치하는 공연정보 여부 확인
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request);	// 조건에 맞는 공연 정보 목록 받아오기
	public ArrayList<TicketDto> getTicket(Long perNum);	// 공연의 예매처 정보 받아오기
	public void deletePastPerformance();	// 시간이 지난 공연 삭제
	public void deleteByRequestT(String requestT);	// requestT와 일치하는 정보 삭제
	public String getUpdateDate();	// DB목록 중 최신 업데이트 날짜 받아오기
	public void deleteAllPerformance();		// 전체 공연 삭제
}
