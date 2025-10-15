package com.tl.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tl.dto.PerformanceDetailDto;
import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceListDto;
import com.tl.dto.PerformancePeaceDto;
import com.tl.dto.PerformanceRequestDto;
import com.tl.dto.PerformanceStatusListDto;
import com.tl.service.ApiService;
import com.tl.service.SchedulerService;
import com.tl.service.PerformanceDBService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j

@Controller
@AllArgsConstructor
@RequestMapping("/tl/*")
public class ApiController {

	ApiService service;
	PerformanceDBService dbService;
	SchedulerService scheService;

	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react에서 전달한 데이터를 ModelAttribute로 자동변환해서 공연 목록 반환
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("공연 정보 요청");
		log.info(requestDto);
		return dbService.getPerformance(requestDto);
	}

	// DB 초기화(초기값 설정)
	@RequestMapping("/resetDB")
	public String resetDB() {
		scheService.resetPerformances();
		
		return "home";
	}

	// 테스트용(db업데이트)
	@RequestMapping("/fetchDB")
	public String fetchDB() {
		scheService.DBFetchTest();
		
		return "home";
	}

	@GetMapping(value = "/getPerformanceInfoApi", produces = "application/json")
	@ResponseBody
	// 테스트용(performanceInfoList 반환)
	public ArrayList<PerformanceInfoDto> getPerformanceInfoApi(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceInfoApi 실행");
		return service.getPIP(requestDto).getPerformanceInfoList();
	}

	// 테스트용(getPerformanceTable.jsp로 이동)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		log.info("getPerformanceInfoTable 실행");
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).getPerformanceInfoList());
		
		return "tl/getPerformanceInfoTable";
	}

	// 테스트용(performanceList 반환)
	@GetMapping("/getPerformanceList")
	@ResponseBody
	public PerformanceListDto getPerformanceList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceList();
	}

	// 테스트용(performanceDetailList 반환)
	@GetMapping("/getPerformanceDetailList")
	@ResponseBody
	public ArrayList<PerformanceDetailDto> getPerformanceDetailList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceDetailList();
	}

	// 테스트용(performancePeaceList 반환)
	@GetMapping("/getPerformancePeaceList")
	@ResponseBody
	public ArrayList<PerformancePeaceDto> getPerformancePeaceList(PerformanceRequestDto requestDto) {
		log.info("getPerformancePeaceList 실행");
		return service.getPIP(requestDto).getPerformancePeaceList();
	}

	// 테스트용(performanceStatusList 반환)
	@GetMapping("/getPerformanceStatusList")
	@ResponseBody
	public PerformanceStatusListDto getPerformanceStatusList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceStatusList 실행");
		return service.getPIP(requestDto).getPerformanceStatusList(); 
	}

}
