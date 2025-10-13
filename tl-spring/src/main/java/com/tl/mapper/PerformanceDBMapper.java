package com.tl.mapper;

import java.util.ArrayList;
import java.util.List;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.TicketDto;

public interface PerformanceDBMapper {
	public void addPerformance(PerformanceInfoDto info);	// db�� ���� ���� �߰�
	public void addTicket(List<TicketDto> ticket);			// db�� ����ó ���� �߰�
	public int checkPerformance(PerformanceInfoDto info);	// ��ġ�ϴ� �������� ���� Ȯ��
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request);	// ���ǿ� �´� ���� ���� ��� �޾ƿ���
	public ArrayList<TicketDto> getTicket(Long perNum);	// ������ ����ó ���� �޾ƿ���
	public void deletePastPerformance();	// �ð��� ���� ���� ����
	public void deleteByRequestT(String requestT);	// requestT�� ��ġ�ϴ� ���� ����
	public String getUpdateDate();	// DB��� �� �ֽ� ������Ʈ ��¥ �޾ƿ���
	public void deleteAllPerformance();		// ��ü ���� ����
}
