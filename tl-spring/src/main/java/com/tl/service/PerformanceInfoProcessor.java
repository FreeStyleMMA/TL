package com.tl.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tl.dto.PerformanceDetailDto;
import com.tl.dto.PerformanceDetailWrapperDto;
import com.tl.dto.PerformanceDto;
import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceListDto;
import com.tl.dto.PerformancePeaceDto;
import com.tl.dto.PerformancePeaceWrapperDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.PerformanceStatusDto;
import com.tl.dto.PerformanceStatusListDto;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Log4j
public class PerformanceInfoProcessor {
	private String apiKey = "da8350e1cfc642d49f9486fb19ade562"; // kopis api key

	private PerformanceListDto performanceList = new PerformanceListDto(); // ���� ��� �迭
	private ArrayList<PerformanceDetailDto> performanceDetailList = new ArrayList<PerformanceDetailDto>(); // ���� �� ����
	private ArrayList<PerformancePeaceDto> performancePeaceList = new ArrayList<PerformancePeaceDto>(); // ������ �� ����
	private PerformanceStatusListDto performanceStatusList = new PerformanceStatusListDto();
	public ArrayList<PerformanceInfoDto> performanceInfoList = new ArrayList<PerformanceInfoDto>(); // ����� �͸� ������ ���� ��
																								// ����

	public PerformanceInfoProcessor(PerformanceRequestDto prd) { // ������ �Լ�
		setPerformanceList(prd);
		setPerformanceDetailList(prd);
		setPerformancePeaceList(performanceDetailList);

		if(isBoxOfficeRequest(prd)) { // ����, ��ŷ ��û�� ��
			setPerformanceInfo(performanceDetailList, performancePeaceList, performanceStatusList.getBoxof());			
		} else {			
			setPerformanceInfo(performanceDetailList, performancePeaceList);
		}
	}

	// kopis���� ���� ��� api �޾ƿ��� �Լ�
	private void setPerformanceList(PerformanceRequestDto prd) {
		// restTemplate�� spring���� �����ϴ� http���� ��� ����
		RestTemplate restTemplate = new RestTemplate();
		// �ѱ� ���� ������ ���� UTF-8 ��ü
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		XmlMapper xmlMapper = new XmlMapper(); // �����Ͱ� XML�����ͱ� ������ xmlmapper ���

		try {
			Thread.sleep(110); // 0.11�� ����
			log.info(prd.requestType);
			// ��õ, ��ŷ ���� ����� ��û �޾��� ��
			if (isBoxOfficeRequest(prd)) { // ����, ��ŷ ��û�� ��
				String API_URL = String.format( // apiKey, prd ����ؼ� api url ����
						"http://kopis.or.kr/openApi/restful/boxoffice?service=%s&stdate=%s&eddate=%s", apiKey,
						prd.startdate, prd.enddate);
				String xmlResponse = restTemplate.getForObject(API_URL, String.class);
				//��ü ����Ʈ �޾ƿ���
				PerformanceStatusListDto temp = xmlMapper.readValue(xmlResponse, PerformanceStatusListDto.class);
				// ��ŷ ���� ����� ���� ����
				if (prd.requestType.equals("recommend")) {
		            Collections.shuffle(temp.getBoxof());
		        }
				// �ִ� 5���� performanceStatusList�� ����
				if (temp != null && temp.getBoxof() != null) {
					int limit = Math.min(5, temp.getBoxof().size());
					performanceStatusList.getBoxof().clear(); // ���� ������ �ʱ�ȭ
					performanceStatusList.getBoxof().addAll(temp.getBoxof().subList(0, limit));
				}
			} else { // �̿� ���� ���
				String API_URL = String.format( // apiKey, prd ����ؼ� api url ����
						"http://kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&cpage=%s&"
								+ "rows=%s&shprfnm=%s&shprfnmfct=%s&shcate=%s&prfplccd=%s&"
								+ "signgucode=%s&signgucodesub=%s&kidstate=%s&prfstate=%s&openrun=%s&afterdate=%s",
						apiKey, prd.startdate, prd.enddate, prd.cpage, prd.rows, prd.shprfnm, prd.shprfnmfct,
						prd.shcate, prd.prfplccd, prd.signgucode, prd.signgucodesub, prd.kidstate, prd.prfstate,
						prd.openrun, prd.afterdate);

				String xmlResponse = restTemplate.getForObject(API_URL, String.class);
				performanceList = xmlMapper.readValue(xmlResponse, PerformanceListDto.class);
			}

		} catch (Exception e) { // ���� ó��
			e.printStackTrace();
			log.info("setPerformanceList ����");
		}
	}

	// �޾ƿ� ���� ����� ���� �� ���� api �޾ƿ��� �Լ�
	private void setPerformanceDetailList(PerformanceRequestDto prd) {
		// restTemplate�� spring���� �����ϴ� http���� ��� ����
		RestTemplate restTemplate = new RestTemplate();
		// �ѱ� ���� ������ ���� UTF-8 ��ü
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// �����Ͱ� XML�����ͱ� ������ xmlmapper ���
		XmlMapper xmlMapper = new XmlMapper();

		// db�� ���� ���� ��� �ݺ�
		if (isBoxOfficeRequest(prd)) { // ����, ��ŷ ��û�� ��
			if (performanceStatusList.getBoxof() != null) {
				for (PerformanceStatusDto perf : performanceStatusList.getBoxof()) {
					String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
							perf.getMt20id(), apiKey);
					try {
						Thread.sleep(110); // 0.11�� ����

						String xmlResponse = restTemplate.getForObject(API_URL, String.class);
						// performanceDetailList�� �޾ƿ� �� ����
						PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse,
								PerformanceDetailWrapperDto.class);
						performanceDetailList.add(detail.getDb());

					} catch (HttpClientErrorException e) { // 400, 404 �� Ŭ���̾�Ʈ ����
						log.error("HTTP ���� �߻�: " + perf.getMt20id() + e.getStatusCode());
						log.error("Response Body: " + e.getResponseBodyAsString()); // ������ �� ���� ���뵵 ���
					} catch (Exception e) { // ��Ÿ ����
						log.error("XML �Ľ�/��� ����: " + perf.getMt20id(), e);
					}
				}
			}
		} else { // �̿� ����� ��
			if (performanceList.getDb() != null) {
				for (PerformanceDto perf : performanceList.getDb()) {
					String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
							perf.getMt20id(), apiKey);
					try {
						Thread.sleep(110); // 0.11�� ����

						String xmlResponse = restTemplate.getForObject(API_URL, String.class);
						// performanceList�� �޾ƿ� �� ����
						PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse,
								PerformanceDetailWrapperDto.class);
						performanceDetailList.add(detail.getDb());

					} catch (HttpClientErrorException e) { // 400, 404 �� Ŭ���̾�Ʈ ����
						log.error("HTTP ���� �߻�: " + perf.getMt20id() + e.getStatusCode());
						log.error("Response Body: " + e.getResponseBodyAsString()); // ������ �� ���� ���뵵 ���
					} catch (Exception e) { // ��Ÿ ����
						log.error("XML �Ľ�/��� ����: " + perf.getMt20id(), e);
					}
				}
			}
		}
	}

	// �޾ƿ� ���� �� ��Ͽ��� �����ü� �� ���� api �޾ƿ��� �Լ�
	private void setPerformancePeaceList(ArrayList<PerformanceDetailDto> performanceDetailList) {
		// restTemplate�� spring���� �����ϴ� http���� ��� ����
		RestTemplate restTemplate = new RestTemplate();
		// �ѱ� ���� ������ ���� UTF-8 ��ü
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// �����Ͱ� XML�����ͱ� ������ xmlmapper ���
		XmlMapper xmlMapper = new XmlMapper();

		// db�� ���� ���� ��� �ݺ�
		if (performanceDetailList != null) {
			for (PerformanceDetailDto perf : performanceDetailList) {
				String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/prfplc/%s?service=%s",
						perf.getMt10id(), apiKey);
				try {
					Thread.sleep(110); // 0.11�� ����

					String xmlResponse = restTemplate.getForObject(API_URL, String.class);
					// performancePeaceList�� �޾ƿ� �� ����
					PerformancePeaceWrapperDto detail = xmlMapper.readValue(xmlResponse,
							PerformancePeaceWrapperDto.class);
					performancePeaceList.add(detail.getDb());

				} catch (HttpClientErrorException e) { // 400, 404 �� Ŭ���̾�Ʈ ����
					log.error("HTTP ���� �߻�: " + perf.getMt20id() + e.getStatusCode());
					log.error("Response Body: " + e.getResponseBodyAsString()); // ������ �� ���� ���뵵 ���
				} catch (Exception e) { // ��Ÿ ����
					log.error("XML �Ľ�/��� ����: " + perf.getMt20id(), e);
				}
			}
		}
	}
	// �޾ƿ� ���� �� ���� api ����
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList,
			ArrayList<PerformancePeaceDto> peaceList) {
		int size = Math.min(detailList.size(), peaceList.size()); // �� ����Ʈ �� ���� ũ������� �ݺ�

		for (int i = 0; i < size; i++) {
			PerformanceDetailDto detail = detailList.get(i);
			PerformancePeaceDto peace = peaceList.get(i);

			performanceInfoList.add(new PerformanceInfoDto(detail, peace));
		}
	}
	// �޾ƿ� ���� �� ���� api ����
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList,
			ArrayList<PerformancePeaceDto> peaceList, List<PerformanceStatusDto> statusList) {
		// �� ����Ʈ �� ���� ũ������� �ݺ�
		int size = Math.min(detailList.size(), Math.min(peaceList.size(), statusList.size()));

		for (int i = 0; i < size; i++) {
			PerformanceDetailDto detail = detailList.get(i);
			PerformancePeaceDto peace = peaceList.get(i);
			PerformanceStatusDto status = statusList.get(i);
			
			performanceInfoList.add(new PerformanceInfoDto(detail, peace, status));
		}
	}
	// ��õ, ��ŷ ���� ��û���� Ȯ��
	private boolean isBoxOfficeRequest(PerformanceRequestDto prd) {
	    return "recommend".equals(prd.requestType) || "rank".equals(prd.requestType);
	}
	// getter
	public PerformanceListDto getPerformanceList() {
		return performanceList;
	}

	public ArrayList<PerformanceDetailDto> getPerformanceDetailList() {
		return performanceDetailList;
	}

	public ArrayList<PerformancePeaceDto> getPerformancePeaceList() {
		return performancePeaceList;
	}

	public PerformanceStatusListDto getPerformanceStatusList() {
		return performanceStatusList;
	}
}
