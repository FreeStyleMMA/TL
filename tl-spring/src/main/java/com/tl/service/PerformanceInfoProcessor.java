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
	
	private PerformanceListDto performanceList = new PerformanceListDto(); // ���� ��� �迭
	private ArrayList<PerformanceDetailDto> performanceDetailList = new ArrayList<PerformanceDetailDto>(); // ���� �� ���� �迭
	public ArrayList<PerformanceInfoDto> performanceinfoList = new ArrayList<PerformanceInfoDto>(); // ����� �͸� ������ ���� �� ����
	
	public PerformanceInfoProcessor(PerformanceRequestDto prd) { // ������ �Լ�
		setPerformanceList(prd);
		setPerformanceDetailList(performanceList);
		setPerformanceInfo(performanceDetailList);
	}

	// kopis���� ���� ��� api �޾ƿ��� �Լ�
	private void setPerformanceList(PerformanceRequestDto prd) {
		String API_URL = String.format( // apiKey, prd ����ؼ� api url ����
			    "http://kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&cpage=%s&"
			    + "rows=%s&shprfnm=%s&shprfnmfct=%s&shcate=%s&prfplccd=%s&"
			    + "signgucode=%s&signgucodesub=%s&kidstate=%s&prfstate=%s&openrun=%s&afterdate=%s",
			    apiKey, prd.startdate, prd.enddate, prd.cpage, prd.rows,
			    prd.shprfnm, prd.shprfnmfct, prd.shcate, prd.prfplccd, prd.signgucode, 
			    prd.signgucodesub, prd.kidstate, prd.prfstate, prd.openrun, prd.afterdate);
		
		// restTemplate�� spring���� �����ϴ� http���� ��� ����
	    RestTemplate restTemplate = new RestTemplate();
	    //�ѱ� ���� ������ ���� UTF-8 ��ü
	    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

	    try {
	        String xmlResponse = restTemplate.getForObject(API_URL, String.class);
	        //�����Ͱ� XML�����ͱ� ������ xmlmapper ���
	        XmlMapper xmlMapper = new XmlMapper();
	        // performanceList�� �޾ƿ� �� ����
	        performanceList = xmlMapper.readValue(xmlResponse, PerformanceListDto.class);
	        
	        Thread.sleep(130);//0.13�� ����
	    } catch (Exception e) { // ���� ó��
	        e.printStackTrace();
	        log.info("setPerformanceList ����");
	    }
	}
	
	// �޾ƿ� ���� ����� ���� �� ���� api �޾ƿ��� �Լ�
	private void setPerformanceDetailList(PerformanceListDto performanceListDto) {
		// restTemplate�� spring���� �����ϴ� http���� ��� ����
        RestTemplate restTemplate = new RestTemplate();
        //�ѱ� ���� ������ ���� UTF-8 ��ü
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //�����Ͱ� XML�����ͱ� ������ xmlmapper ���
        XmlMapper xmlMapper = new XmlMapper();

        // db�� ���� ���� ��� �ݺ�
        if (performanceListDto.getDb() != null) {
            for (PerformanceDto perf : performanceListDto.getDb()) {
            	String API_URL = String.format(
            			"http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
            			perf.getMt20id(), apiKey
            			);
            	try {
            		String xmlResponse = restTemplate.getForObject(API_URL, String.class);
        	        // performanceList�� �޾ƿ� �� ����
        	        PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse, PerformanceDetailWrapperDto.class);
            	    performanceDetailList.add(detail.getDb());

            	    Thread.sleep(130); // 0.13�� ����

            	} catch (HttpClientErrorException e) { // 400, 404 �� Ŭ���̾�Ʈ ����
            	    log.error("HTTP ���� �߻�: "+ perf.getMt20id()+ e.getStatusCode());
            	    log.error("Response Body: "+ e.getResponseBodyAsString()); // ������ �� ���� ���뵵 ���
            	} catch (Exception e) { // ��Ÿ ����
            	    log.error("XML �Ľ�/��� ����: "+ perf.getMt20id(), e);
            	}
            }
        }
    }
	
	//�޾ƿ� ���� �� ���� api ����
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList) {
		for(PerformanceDetailDto detail : detailList) {
			performanceinfoList.add(new PerformanceInfoDto(detail.getPrfnm(), detail.getPrfpdfrom(),
					detail.getPrfpdto(),detail.getFcltynm(), detail.getPrfruntime(), detail.getDtguidance(),
					detail.getGenrenm(), detail.getPoster(), detail.getRelates()));
		}
	}
}
