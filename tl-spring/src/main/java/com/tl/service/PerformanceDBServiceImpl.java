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


	// api���� �޾ƿ� ���� ������ db�� ����
	@Override
	public void addPerformance(ArrayList<PerformanceInfoDto> pfmInfo) {
		// perId������ ��ġ�ϴ� ���� �ִ��� Ȯ�� �� ������ db�� ����
		log.info("����" + pfmInfo.size());
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

			}
		}
	}

	// db���� ���ǿ� �´� ���� ���� ��������
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


	// db���� ���� ���� �����
	@Override
	public void fetchPerformance() {
		mapper.deletePastPerformance();
		mapper.deleteByRequestT("rank");
		mapper.deleteByRequestT("rankConcert");
		mapper.deleteByRequestT("rankMusical");
		mapper.deleteByRequestT("rankTheatre");
	}
	
	// �ֽ� ������Ʈ ��¥ �޾ƿ���
	@Override
	public String getUpdateDate() {
		String updateD = mapper.getUpdateDate();
		return updateD;
	}
	
	// DB ��ü ����
	@Override
	public void resetPerformance() {
		mapper.deleteAllPerformance();
	}
}
