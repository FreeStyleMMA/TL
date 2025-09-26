package com.tl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;

public interface TicketMapper {
	public void addPerformance(PerformanceInfoDto info);
	public void addTicket(List<TicketDto> ticket);
	public int checkPerformance(PerformanceInfoDto info);
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request);
	public ArrayList<TicketDto> getTicket(Long perNum);
}
