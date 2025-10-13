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

	// 호출 시 DB에서 데이터를 찾아서 ArrayList<PerformanceInfoDto> 반환
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {
		return dbService.getPerformance(requestDto);
	}

	// 테스트용(DB 업데이트)
	@RequestMapping("/fetchDB")
	public String fetchDB() {
		scheService.DBFetchTest();

		return "redirect:/home";
	}

	// 관리자용(DB 초기화)
	@RequestMapping("/resetDB")
	public String resetDB() {
		scheService.resetPerformances();

		return "redirect:/home";
	}

	@GetMapping(value = "/getPerformanceInfoApi", produces = "application/json")
	@ResponseBody
	// 테스트용(performanceInfoList 출력)
	public ArrayList<PerformanceInfoDto> getPerformanceInfoApi(@ModelAttribute PerformanceRequestDto requestDto) {
		return service.getPIP(requestDto).getPerformanceInfoList();
	}

	// 테스트용(호출 시 getPerformanceInfo.jsp로 이동)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList 전달
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).getPerformanceInfoList());

		return "tl/getPerformanceInfoTable";
	}

	// 테스트용(performanceList 출력)
	@GetMapping("/getPerformanceList")
	@ResponseBody
	public PerformanceListDto getPerformanceList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceList(); // performanceList만 가져오기
	}

	// 테스트용(performanceDetailList 출력)
	@GetMapping("/getPerformanceDetailList")
	@ResponseBody
	public ArrayList<PerformanceDetailDto> getPerformanceDetailList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceDetailList(); // performanceDetailList만 가져오기
	}

	// 테스트용(performancePeaceList 출력)
	@GetMapping("/getPerformancePeaceList")
	@ResponseBody
	public ArrayList<PerformancePeaceDto> getPerformancePeaceList(PerformanceRequestDto requestDto) {
		log.info("getPerformancePeaceList 실행");
		return service.getPIP(requestDto).getPerformancePeaceList(); // performancePeaceList만 가져오기
	}

	// 테스트용(performanceStatusList 출력)
	@GetMapping("/getPerformanceStatusList")
	@ResponseBody
	public PerformanceStatusListDto getPerformanceStatusList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceStatusList 실행");
		return service.getPIP(requestDto).getPerformanceStatusList(); // performanceStatusList만 가져오기
	}

}
