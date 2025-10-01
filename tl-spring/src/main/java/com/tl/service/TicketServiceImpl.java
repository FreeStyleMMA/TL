package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;
import com.tl.mapper.TicketMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class TicketServiceImpl implements TicketService {
	@Setter(onMethod_ = @Autowired)
	public TicketMapper mapper;

	//api���� �޾ƿ� ���� ������ db�� ����
	@Override
	public void addPerformance(ArrayList<PerformanceInfoDto> pfmInfo) {
		// perId������ ��ġ�ϴ� ���� �ִ��� Ȯ�� �� ������ db�� ����
		for (PerformanceInfoDto info : pfmInfo) {
			if (mapper.checkPerformance(info) == 0) {
				// performance ����(perNum ����)
				mapper.addPerformance(info);
				// Ƽ�Ͽ� perNum ����
				if (info.getPerTicket() != null) {
					for (TicketDto ticket : info.getPerTicket()) {
						ticket.setPerNum(info.getPerNum());
					}
					// Ƽ�� ����
					mapper.addTicket(info.getPerTicket());
				}
			} else {
				log.info("�̹� �����ϴ� ����");
			}
		}
	}
	@Override
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request) {
		ArrayList<PerformanceInfoDto> infos = mapper.getPerformance(request);
		log.info(infos);
		for(PerformanceInfoDto info : infos) {
			ArrayList<TicketDto> tickets = mapper.getTicket(info.getPerNum());
			info.setPerTicket(tickets);
		}
		return infos;
	}
	
	public ArrayList<PerformanceInfoDto> getHomePerform(){
		return mapper.getHomePerform();
	}
}
