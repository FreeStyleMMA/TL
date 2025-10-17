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
   
   // DB 업데이트 실행(테스트용)
   public void DBFetchTest() {
      fetchPerformances();
   }

   //매일 18:30 실행(kopis api 업데이트 18:00)
   @Scheduled(cron = "0 30 18 * * ?")
   private void fetchPerformances() {
       log.info("DB 업데이트");
       // db 목록 정리
       dbService.fetchPerformance();
       ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
       String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
       String oneMonthsAgo = LocalDate.now().minusDays(30).format(DateTimeFormatter.BASIC_ISO_DATE);
       String oneMonthsLater = LocalDate.now().plusDays(30).format(DateTimeFormatter.BASIC_ISO_DATE);
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
           log.warn("API 호출 대기 중 인터럽트 발생", e);
       }
       //rank 목록 추가
       addRankPerformances(oneMonthsAgo, today);
      log.info("db업데이트 완료");
    }
   
   // db 초기화(초기값 설정)
   public void resetPerformances() {
       log.info("DB 초기화");
        // DB 초기화
       dbService.resetPerformance();
        // db 목록 추가
       ArrayList<PerformanceInfoDto> infos = new ArrayList<PerformanceInfoDto>();
       String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
       String oneMonthsAgo = LocalDate.now().minusDays(30).format(DateTimeFormatter.BASIC_ISO_DATE);
       String oneMonthsLater = LocalDate.now().plusDays(30).format(DateTimeFormatter.BASIC_ISO_DATE);
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
           log.warn("API 호출 대기 중 인터럽트 발생", e);
       }
       // rank db 목록 추가
       addRankPerformances(oneMonthsAgo, today);
      log.info("db초기화 완료");
    }
   // db에 rank목록 추가
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
