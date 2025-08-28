package com.tl.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tl.dto.PerformanceDetailListDto;
import com.tl.dto.PerformanceDto;
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
	
	public PerformanceListDto performanceList = new PerformanceListDto(); // ���� ��� �迭
	public ArrayList<PerformanceDetailListDto> performanceDetailList = new ArrayList<PerformanceDetailListDto>(); // ���� �� ���� �迭
	
	public PerformanceInfoProcessor(PerformanceRequestDto prd) { // ������ �Լ�
		setPerformanceList(prd);
		setPerformanceDetailList(performanceList);
	}

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
	        
	        Thread.sleep(100);//0.1�� ����
	    } catch (Exception e) { // ���� ó��
	        e.printStackTrace();
	        log.info("setPerformanceList ����");
	    }
	}
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
            			perf.mt20id, apiKey
            			);
            	try {
            		String xmlResponse = restTemplate.getForObject(API_URL, String.class);
        	        // performanceList�� �޾ƿ� �� ����
        	        PerformanceDetailListDto detail = xmlMapper.readValue(xmlResponse, PerformanceDetailListDto.class);
            	    performanceDetailList.add(detail);

            	    Thread.sleep(100); // 0.1�� ����

            	} catch (HttpClientErrorException e) { // 400, 404 �� Ŭ���̾�Ʈ ����
            	    log.error("HTTP ���� �߻�: "+ perf.mt20id+ e.getStatusCode());
            	    log.error("Response Body: "+ e.getResponseBodyAsString()); // ������ �� ���� ���뵵 ���
            	} catch (Exception e) { // ��Ÿ ����
            	    log.error("XML �Ľ�/��� ����: "+ perf.mt20id, e);
            	}
            }
        }
    }
}
