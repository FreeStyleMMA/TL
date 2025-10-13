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
	
	// 테스트용
	public void DBFetchTest() {
		fetchPerformances();
	}

	// 매일 18:30에 실행(kopis 공연 api 업데이트가 18시에 되기 때문)
	@Scheduled(cron = "0 30 18 * * ?")
	private void fetchPerformances() {
    	log.info("DB 업데이트");
        // 기간 지난 데이터 삭제
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	dbService.fetchPerformance();
        // 전체 공연 api 저장
    	String twoWeeksAgo = LocalDate.now().minusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksLater = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	int page = 1;
    	while(true) {
    		log.info("업데이트 날짜:" + dbService.getUpdateDate());
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
    	//랭킹 api 저장
    	infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.ALL_CODE, "rank")).getPerformanceInfoList();
    	dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.CONCERT_CODE, "rankConcert")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.MUSICAL_CODE, "rankMusical")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.THEATRE_CODE, "rankTheatre")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		log.info("db업데이트 완료");
    }
	
	public void resetPerformances() {
    	log.info("DB 초기화");
        // 1. DB 초기화
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	dbService.fetchPerformance();
        // 전체 공연 api 저장
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
    	//랭킹 api 저장
    	infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.ALL_CODE, "rank")).getPerformanceInfoList();
    	dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.CONCERT_CODE, "rankConcert")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.MUSICAL_CODE, "rankMusical")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		infos = apiService.getPIP(new PerformanceRequestDto(twoWeeksAgo, twoWeeksLater, Common.THEATRE_CODE, "rankTheatre")).getPerformanceInfoList();
		dbService.addPerformance(infos);
		log.info("db초기화 완료");
    }
}
