package com.tl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;

public interface PerformanceDBMapper {
	public void addPerformance(PerformanceInfoDto info);	// db에 목록 추가
	public void addTicket(List<TicketDto> ticket);			// db에 목록 추가
	public int checkPerformance(PerformanceInfoDto info);	// 일치하는 공연 여부 확인
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request);	// 요청에 맞는 목록 얻기
	public ArrayList<TicketDto> getTicket(Long perNum);	// perNum이 맞는 performance_ticket 목록 얻기
	public void deletePastPerformance();	// 날짜 경과한 목록 삭제
	public void deleteByRequestT(String requestT);	// requestT가 일치하는 목록 삭제
	public void deleteAllPerformance();		// db목록 전부 삭제
}
