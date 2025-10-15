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
	
	// DB ������Ʈ ����(�׽�Ʈ��)
	public void DBFetchTest() {
		fetchPerformances();
	}

	//���� 18:30 ����(kopis api ������Ʈ 18:00)
	@Scheduled(cron = "0 30 18 * * ?")
	private void fetchPerformances() {
    	log.info("DB ������Ʈ");
    	// db ��� ����
    	dbService.fetchPerformance();
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	String oneMonthsLater = LocalDate.now().plusMonths(1).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksAgo = LocalDate.now().minusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksLater = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	int page = 1;
    	while(true) {
    		infos = apiService.getPIP(new PerformanceRequestDto(oneMonthsLater, oneMonthsLater, page,
    			Common.MAX_REQUEST, Common.ALL_CODE)).getPerformanceInfoList();
    		if(infos.isEmpty()) {
    			break;
    		}
    		dbService.addPerformance(infos);
    		if(infos.size() < Common.MAX_REQUEST) {
    			break;
    		}
    		page++;
    	}
    	try {
    	    Thread.sleep(1000);
    	} catch (InterruptedException e) {
    	    Thread.currentThread().interrupt();
    	    log.warn("API ȣ�� ��� �� ���ͷ�Ʈ �߻�", e);
    	}
    	//rank ��� �߰�
    	addRankPerformances(twoWeeksAgo, twoWeeksLater);
		log.info("db������Ʈ �Ϸ�");
    }
	
	// db �ʱ�ȭ(�ʱⰪ ����)
	public void resetPerformances() {
    	log.info("DB �ʱ�ȭ");
        // DB �ʱ�ȭ
    	dbService.resetPerformance();
        // db ��� �߰�
    	ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
    	String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    	String oneMonthsLater = LocalDate.now().plusMonths(1).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksAgo = LocalDate.now().minusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	String twoWeeksLater = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.BASIC_ISO_DATE);
    	int page = 1;
    	while(true) {
    		infos = apiService.getPIP(new PerformanceRequestDto(today, oneMonthsLater, page, 
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
    	try {
    	    Thread.sleep(10000);
    	} catch (InterruptedException e) {
    	    Thread.currentThread().interrupt();
    	    log.warn("API ȣ�� ��� �� ���ͷ�Ʈ �߻�", e);
    	}
    	// rank db ��� �߰�
    	addRankPerformances(twoWeeksAgo, twoWeeksLater);
		log.info("db�ʱ�ȭ �Ϸ�");
    }
	// db�� rank��� �߰�
	private void addRankPerformances(String startDate, String endDate) {
        ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();

        String[] rankTypes = {
            "rank", "rankConcert", "rankMusical", "rankTheatre"
        };
        String[] genreCodes = {
            Common.ALL_CODE, Common.CONCERT_CODE, Common.MUSICAL_CODE, Common.THEATRE_CODE
        };

        for (int i = 0; i < rankTypes.length; i++) {
            infos = apiService.getPIP(new PerformanceRequestDto(startDate, endDate, genreCodes[i], rankTypes[i]))
                    .getPerformanceInfoList();

            dbService.addPerformance(infos);
        }
    }
}
