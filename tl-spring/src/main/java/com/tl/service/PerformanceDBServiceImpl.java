package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;
import com.tl.mapper.PerformanceDBMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PerformanceDBServiceImpl implements PerformanceDBService {
	@Setter(onMethod_ = @Autowired)
	public PerformanceDBMapper mapper;


	// 받아온 api를 db목록에 저장
	@Override
	@Transactional
	public void addPerformance(ArrayList<PerformanceInfoDto> pfmInfo) {
		// db에 저장
		log.info("db 길이 : " + pfmInfo.size());
		int test = 0;
		for (PerformanceInfoDto info : pfmInfo) {
			if (mapper.checkPerformance(info) == 0) {
				mapper.addPerformance(info);
				// ticket db에 저장
				if (info.getPerTicket() != null) {
					for (TicketDto ticket : info.getPerTicket()) {
						ticket.setPerNum(info.getPerNum());
					}
					mapper.addTicket(info.getPerTicket());
				}
			}
		}
	}

	// 조건과 일치하는 db목록 가져오기
	@Override
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request) {
		ArrayList<PerformanceInfoDto> infos = mapper.getPerformance(request);
		log.info(infos);
		for (PerformanceInfoDto info : infos) {
			ArrayList<TicketDto> tickets = mapper.getTicket(info.getPerNum());
			info.setPerTicket(tickets);
		}
		return infos;
	}


	// db 목록 정리
	@Override
	public void fetchPerformance() {
		mapper.deletePastPerformance();
		mapper.deleteByRequestT("rank");
		mapper.deleteByRequestT("rankConcert");
		mapper.deleteByRequestT("rankMusical");
		mapper.deleteByRequestT("rankTheatre");
	}
	
	// DB 목록 모두 삭제
	@Override
	public void resetPerformance() {
		mapper.deleteAllPerformance();
	}
}
