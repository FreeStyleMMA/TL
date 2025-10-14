package com.tl.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.util.Common;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class SchedulerService {
	private ApiService apiService;
	private PerformanceDBService dbService;

	public SchedulerService(ApiService apiService, PerformanceDBService dbService) {
		this.apiService = apiService;
		this.dbService = dbService;
	}
	
	// DB 공연 업데이트 테스트
	public void DBFetchTest() {
		fetchPerformances();
	}

	//매일 18:30 스케쥴링(kopis 공연 api DB에 업데이트)
	@Scheduled(cron = "0 30 18 * * ?")
//	@Scheduled(cron = "0 * * * * ?")// 테스트용
	private void fetchPerformances() {
    	log.info("DB ������Ʈ");
        // �Ⱓ ���� ������ ����
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	dbService.fetchPerformance();
        // ��ü ���� api ����
    	String twoWeeksAgo = LocalDate.now().minusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksLater = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	int page = 1;
    	while(true) {
    		log.info("������Ʈ ��¥:" + dbService.getUpdateDate());
    		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, page,
    			Common.MAX_REQUEST, Common.ALL_CODE, dbService.getUpdateDate())).getPerformanceInfoList();
    		if(infos.isEmpty()) {
    			break;
    		}
    		dbService.addPerformance(infos);
    		if(infos.size() < Common.MAX_REQUEST) {
    			break;
    		}
    		page++;
    	}
    	//��ŷ api ����
    	infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.ALL_CODE, "rank")).getPerformanceInfoList();
    	dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.CONCERT_CODE, "rankConcert")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.MUSICAL_CODE, "rankMusical")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.THEATRE_CODE, "rankTheatre")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		log.info("db������Ʈ �Ϸ�");
    }
	
	public void resetPerformances() {
    	log.info("DB �ʱ�ȭ");
        // 1. DB �ʱ�ȭ
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	dbService.fetchPerformance();
        // ��ü api ����
    	String twoWeeksAgo = LocalDate.now().minusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksLater = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	int page = 1;
    	while(true) {
    		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, page, 
    			Common.MAX_REQUEST, Common.ALL_CODE)).getPerformanceInfoList();
    		if(infos.isEmpty()) {
    			break;
    		}
    		if(infos.size() < Common.MAX_REQUEST) {
    			dbService.addPerformance(infos);
    			break;
    		}
    		dbService.addPerformance(infos);
    		page++;
    	}
    	//��ŷ api ����
    	infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.ALL_CODE, "rank")).getPerformanceInfoList();
    	dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.CONCERT_CODE, "rankConcert")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.MUSICAL_CODE, "rankMusical")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.THEATRE_CODE, "rankTheatre")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		log.info("db�ʱ�ȭ �Ϸ�");
    }

}
