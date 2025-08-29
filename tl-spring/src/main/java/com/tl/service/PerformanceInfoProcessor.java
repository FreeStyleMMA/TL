package com.tl.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tl.dto.PerformanceDetailDto;
import com.tl.dto.PerformanceDetailWrapperDto;
import com.tl.dto.PerformanceDto;
import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceListDto;
import com.tl.dto.PerformanceRequestDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Log4j
public class PerformanceInfoProcessor {
	private String apiKey = "da8350e1cfc642d49f9486fb19ade562"; // kopis api key
	
	private PerformanceListDto performanceList = new PerformanceListDto(); // 공연 목록 배열
	private ArrayList<PerformanceDetailDto> performanceDetailList = new ArrayList<PerformanceDetailDto>(); // 공연 상세 정보 배열
	public ArrayList<PerformanceInfoDto> performanceinfoList = new ArrayList<PerformanceInfoDto>(); // 사용할 것만 정리한 공연 상세 정보
	
	public PerformanceInfoProcessor(PerformanceRequestDto prd) { // 생성자 함수
		setPerformanceList(prd);
		setPerformanceDetailList(performanceList);
		setPerformanceInfo(performanceDetailList);
	}

	// kopis에서 공연 목록 api 받아오는 함수
	private void setPerformanceList(PerformanceRequestDto prd) {
		String API_URL = String.format( // apiKey, prd 사용해서 api url 생성
			    "http://kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&cpage=%s&"
			    + "rows=%s&shprfnm=%s&shprfnmfct=%s&shcate=%s&prfplccd=%s&"
			    + "signgucode=%s&signgucodesub=%s&kidstate=%s&prfstate=%s&openrun=%s&afterdate=%s",
			    apiKey, prd.startdate, prd.enddate, prd.cpage, prd.rows,
			    prd.shprfnm, prd.shprfnmfct, prd.shcate, prd.prfplccd, prd.signgucode, 
			    prd.signgucodesub, prd.kidstate, prd.prfstate, prd.openrun, prd.afterdate);
		
		// restTemplate은 spring에서 제공하는 http와의 통신 도구
	    RestTemplate restTemplate = new RestTemplate();
	    //한글 깨짐 방지를 위한 UTF-8 교체
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

	    try {
	        String xmlResponse = restTemplate.getForObject(API_URL, String.class);
	        //데이터가 XML데이터기 때문에 xmlmapper 사용
	        XmlMapper xmlMapper = new XmlMapper();
	        // performanceList에 받아온 값 저장
	        performanceList = xmlMapper.readValue(xmlResponse, PerformanceListDto.class);
	        
	        Thread.sleep(130);//0.13초 지연
	    } catch (Exception e) { // 예외 처리
	        e.printStackTrace();
	        log.info("setPerformanceList 실패");
	    }
	}
	
	// 받아온 공연 목록의 공연 상세 정보 api 받아오는 함수
	private void setPerformanceDetailList(PerformanceListDto performanceListDto) {
		// restTemplate은 spring에서 제공하는 http와의 통신 도구
        RestTemplate restTemplate = new RestTemplate();
        //한글 깨짐 방지를 위한 UTF-8 교체
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //데이터가 XML데이터기 때문에 xmlmapper 사용
        XmlMapper xmlMapper = new XmlMapper();

        // db가 여러 개일 경우 반복
        if (performanceListDto.getDb() != null) {
            for (PerformanceDto perf : performanceListDto.getDb()) {
            	String API_URL = String.format(
            			"http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
            			perf.getMt20id(), apiKey
            			);
            	try {
            		String xmlResponse = restTemplate.getForObject(API_URL, String.class);
        	        // performanceList에 받아온 값 저장
        	        PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse, PerformanceDetailWrapperDto.class);
            	    performanceDetailList.add(detail.getDb());

            	    Thread.sleep(130); // 0.13초 지연

            	} catch (HttpClientErrorException e) { // 400, 404 등 클라이언트 오류
            	    log.error("HTTP 오류 발생: "+ perf.getMt20id()+ e.getStatusCode());
            	    log.error("Response Body: "+ e.getResponseBodyAsString()); // 서버가 준 오류 내용도 출력
            	} catch (Exception e) { // 기타 예외
            	    log.error("XML 파싱/통신 오류: "+ perf.getMt20id(), e);
            	}
            }
        }
    }
	
	//받아온 공연 상세 정보 api 정리
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList) {
		for(PerformanceDetailDto detail : detailList) {
			performanceinfoList.add(new PerformanceInfoDto(detail.getPrfnm(), detail.getPrfpdfrom(),
					detail.getPrfpdto(),detail.getFcltynm(), detail.getPrfruntime(), detail.getDtguidance(),
					detail.getGenrenm(), detail.getPoster(), detail.getRelates()));
		}
	}
}
