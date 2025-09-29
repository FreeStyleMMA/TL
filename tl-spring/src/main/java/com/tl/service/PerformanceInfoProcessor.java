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

	private PerformanceListDto performanceList = new PerformanceListDto(); // 공연 목록 배열
	private ArrayList<PerformanceDetailDto> performanceDetailList = new ArrayList<PerformanceDetailDto>(); // 공연 상세 정보
	private ArrayList<PerformancePeaceDto> performancePeaceList = new ArrayList<PerformancePeaceDto>(); // 공연장 상세 정보
	private PerformanceStatusListDto performanceStatusList = new PerformanceStatusListDto();
	public ArrayList<PerformanceInfoDto> performanceInfoList = new ArrayList<PerformanceInfoDto>(); // 사용할 것만 정리한 공연 상세
	// 정보

	public PerformanceInfoProcessor(PerformanceRequestDto prd) { // 생성자 함수
		setPerformanceList(prd);
		setPerformanceDetailList(prd);
		setPerformancePeaceList(performanceDetailList);

		if (isBoxOfficeRequest(prd)) { // 공연, 랭킹 요청일 때
			setPerformanceInfo(performanceDetailList, performancePeaceList, performanceStatusList.getBoxof(), prd);
		} else {
			setPerformanceInfo(performanceDetailList, performancePeaceList, prd);
		}
	}

	// kopis에서 공연 목록 api 받아오는 함수
	private void setPerformanceList(PerformanceRequestDto prd) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		XmlMapper xmlMapper = new XmlMapper();

		try {
			Thread.sleep(110); // 0.11초 지연
			log.info(prd.getPerRequestT());

			// 추천, 랭킹 공연 목록을 요청 받았을 때
			if (isBoxOfficeRequest(prd)) {
				String API_URL = String.format(
						"http://kopis.or.kr/openApi/restful/boxoffice?service=%s&stdate=%s&eddate=%s&catecode=%s",
						apiKey, prd.getStartdate(), prd.getEnddate(), prd.getShcate());
				String xmlResponse = restTemplate.getForObject(API_URL, String.class);
				PerformanceStatusListDto temp = xmlMapper.readValue(xmlResponse, PerformanceStatusListDto.class);
				// 추천 공연 일때 배열 무작위로 섞기
				if ("recommend".equals(prd.getPerRequestT())) {
					Collections.shuffle(temp.getBoxof());
				}
				// 공연 시작 인덱스, 가져올 개수 정하기
				if (temp != null && temp.getBoxof() != null && prd.getCpage() != null && prd.getRows() != null) {
					int start = (Integer.parseInt(prd.getCpage()) - 1) * Integer.parseInt(prd.getRows());
					int limit = Math.min(start + Integer.parseInt(prd.getRows()), temp.getBoxof().size());

					performanceStatusList.getBoxof().clear();
					performanceStatusList.getBoxof().addAll(temp.getBoxof().subList(start, limit));
				}
			} else { // 이외 공연 목록
				String API_URL = String.format(
						"http://kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&cpage=%s&"
								+ "rows=%s&shprfnm=%s&shprfnmfct=%s&shcate=%s&prfplccd=%s&"
								+ "signgucode=%s&signgucodesub=%s&kidstate=%s&prfstate=%s&openrun=%s&afterdate=%s",
						apiKey, prd.getStartdate(), prd.getEnddate(), prd.getCpage(), prd.getRows(), prd.getShprfnm(),
						prd.getShprfnmfct(), prd.getShcate(), prd.getPrfplccd(), prd.getSigngucode(),
						prd.getSigngucodesub(), prd.getKidstate(), prd.getPrfstate(), prd.getOpenrun(),
						prd.getAfterdate());

				String xmlResponse = restTemplate.getForObject(API_URL, String.class);
				performanceList = xmlMapper.readValue(xmlResponse, PerformanceListDto.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("setPerformanceList 실패");
		}
	}

	// 받아온 공연 목록의 공연 상세 정보 api 받아오는 함수
	private void setPerformanceDetailList(PerformanceRequestDto prd) {
		// restTemplate은 spring에서 제공하는 http와의 통신 도구
		RestTemplate restTemplate = new RestTemplate();
		// 한글 깨짐 방지를 위한 UTF-8 교체
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// 데이터가 XML데이터기 때문에 xmlmapper 사용
		XmlMapper xmlMapper = new XmlMapper();

		// db가 여러 개일 경우 반복
		if (isBoxOfficeRequest(prd)) { // 공연, 랭킹 요청일 때
			if (performanceStatusList.getBoxof() != null) {
				for (PerformanceStatusDto perf : performanceStatusList.getBoxof()) {
					String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
							perf.getMt20id(), apiKey);
					try {
						Thread.sleep(110); // 0.11초 지연

						String xmlResponse = restTemplate.getForObject(API_URL, String.class);
						// performanceDetailList에 받아온 값 저장
						PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse,
								PerformanceDetailWrapperDto.class);
						performanceDetailList.add(detail.getDb());

					} catch (HttpClientErrorException e) { // 400, 404 등 클라이언트 오류
						log.error("HTTP 오류 발생: " + perf.getMt20id() + e.getStatusCode());
						log.error("Response Body: " + e.getResponseHeaders()); // 서버가 준 오류 내용도 출력
					} catch (Exception e) { // 기타 예외
						log.error("XML 파싱/통신 오류: " + perf.getMt20id(), e);
					}
				}
			}
		} else { // 이외 목록일 때
			if (performanceList.getDb() != null) {
				for (PerformanceDto perf : performanceList.getDb()) {
					String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/pblprfr/%s?service=%s",
							perf.getMt20id(), apiKey);
					try {
						Thread.sleep(110); // 0.11초 지연

						String xmlResponse = restTemplate.getForObject(API_URL, String.class);
						// performanceList에 받아온 값 저장
						PerformanceDetailWrapperDto detail = xmlMapper.readValue(xmlResponse,
								PerformanceDetailWrapperDto.class);
						performanceDetailList.add(detail.getDb());

					} catch (HttpClientErrorException e) { // 400, 404 등 클라이언트 오류
						log.error("HTTP 오류 발생: " + perf.getMt20id() + e.getStatusCode());
						log.error("Response Body: " + e.getResponseBodyAsString()); // 서버가 준 오류 내용도 출력
					} catch (Exception e) { // 기타 예외
						log.error("XML 파싱/통신 오류: " + perf.getMt20id(), e);
					}
				}
			}
		}
	}

	// 받아온 공연 상세 목록에서 공연시설 상세 정보 api 받아오는 함수
	private void setPerformancePeaceList(ArrayList<PerformanceDetailDto> performanceDetailList) {
		// restTemplate은 spring에서 제공하는 http와의 통신 도구
		RestTemplate restTemplate = new RestTemplate();
		// 한글 깨짐 방지를 위한 UTF-8 교체
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// 데이터가 XML데이터기 때문에 xmlmapper 사용
		XmlMapper xmlMapper = new XmlMapper();

		// db가 여러 개일 경우 반복
		if (performanceDetailList != null) {
			for (PerformanceDetailDto perf : performanceDetailList) {
				String API_URL = String.format("http://www.kopis.or.kr/openApi/restful/prfplc/%s?service=%s",
						perf.getMt10id(), apiKey);
				try {
					Thread.sleep(110); // 0.11초 지연

					String xmlResponse = restTemplate.getForObject(API_URL, String.class);
					// performancePeaceList에 받아온 값 저장
					PerformancePeaceWrapperDto detail = xmlMapper.readValue(xmlResponse,
							PerformancePeaceWrapperDto.class);
					performancePeaceList.add(detail.getDb());

				} catch (HttpClientErrorException e) { // 400, 404 등 클라이언트 오류
					log.error("HTTP 오류 발생: " + perf.getMt20id() + e.getStatusCode());
					log.error("Response Body: " + e.getResponseBodyAsString()); // 서버가 준 오류 내용도 출력
				} catch (Exception e) { // 기타 예외
					log.error("XML 파싱/통신 오류: " + perf.getMt20id(), e);
				}
			}
		}
	}

	// 받아온 공연 상세 정보 api 정리
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList,
			ArrayList<PerformancePeaceDto> peaceList, PerformanceRequestDto prd) {
		int size = Math.min(detailList.size(), peaceList.size()); // 두 리스트 중 작은 크기까지만 반복

		for (int i = 0; i < size; i++) {
			PerformanceDetailDto detail = detailList.get(i);
			PerformancePeaceDto peace = peaceList.get(i);

			performanceInfoList.add(new PerformanceInfoDto(detail, peace, prd));
		}
	}

	// 받아온 공연 상세 정보 api 정리
	private void setPerformanceInfo(ArrayList<PerformanceDetailDto> detailList,
			ArrayList<PerformancePeaceDto> peaceList, List<PerformanceStatusDto> statusList,
			PerformanceRequestDto prd) {
		// 세 리스트 중 작은 크기까지만 반복
		int size = Math.min(detailList.size(), Math.min(peaceList.size(), statusList.size()));

		for (int i = 0; i < size; i++) {
			PerformanceDetailDto detail = detailList.get(i);
			PerformancePeaceDto peace = peaceList.get(i);
			PerformanceStatusDto status = statusList.get(i);

			performanceInfoList.add(new PerformanceInfoDto(detail, peace, status, prd));
		}
	}

	// 추천, 랭킹 공연 요청인지 확인
	private boolean isBoxOfficeRequest(PerformanceRequestDto prd) {
		return "recommend".equals(prd.getPerRequestT()) || "rank".equals(prd.getPerRequestT());
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
