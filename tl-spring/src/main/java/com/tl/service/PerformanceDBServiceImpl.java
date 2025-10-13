package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	// api에서 받아온 공연 데이터 db에 저장
	@Override
	public void addPerformance(ArrayList<PerformanceInfoDto> pfmInfo) {
		// perId값으로 일치하는 공연 있는지 확인 후 없으면 db에 저장
		log.info("길이" + pfmInfo.size());
		for (PerformanceInfoDto info : pfmInfo) {
			if (mapper.checkPerformance(info) == 0) {
				// performance 저장(perNum 생성)
				mapper.addPerformance(info);
				// 티켓에 perNum 세팅
				if (info.getPerTicket() != null) {
					for (TicketDto ticket : info.getPerTicket()) {
						ticket.setPerNum(info.getPerNum());
					}
					// 티켓 저장
					mapper.addTicket(info.getPerTicket());
				}
			}
		}
	}

	// db에서 조건에 맞는 공연 정보 가져오기
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

	// db에서 지울 내용 지우기
	@Override
	public void fetchPerformance() {
		mapper.deletePastPerformance();
		mapper.deleteByRequestT("rank");
		mapper.deleteByRequestT("rankConcert");
		mapper.deleteByRequestT("rankMusical");
		mapper.deleteByRequestT("rankTheatre");
	}
	
	// 최신 업데이트 날짜 받아오기
	@Override
	public String getUpdateDate() {
		String updateD = mapper.getUpdateDate();
		return updateD;
	}
	
	// DB 전체 삭제
	@Override
	public void resetPerformance() {
		mapper.deleteAllPerformance();
	}
}
