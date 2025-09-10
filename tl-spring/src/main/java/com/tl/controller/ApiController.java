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

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j

@Controller
@AllArgsConstructor
@RequestMapping("/tl/*")
public class ApiController {

	ApiService service;

	// 호출 시 getPerformanceInfo.jsp로 이동(api 정보 테스트용)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList 전달
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).performanceInfoList);

		return "tl/getPerformanceInfoTable";
	}

	// 호출 시 PerformanceInfoProcessor 반환(실제 사용할 반환용)
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceInfo 실행");
		return service.getPIP(requestDto).performanceInfoList; // performanceInfoList만 가져오기
	}
	
	// 테스트용(performanceList 출력)
	@GetMapping("/getPerformanceList")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public PerformanceListDto getPerformanceList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceList(); // performanceList만 가져오기
	}
	// 테스트용(performanceDetailList 출력)
	@GetMapping("/getPerformanceDetailList")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformanceDetailDto> getPerformanceDetailList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceList 실행");
		return service.getPIP(requestDto).getPerformanceDetailList(); // performanceDetailList만 가져오기
	}
	// 테스트용(performancePeaceList 출력)
	@GetMapping("/getPerformancePeaceList")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformancePeaceDto> getPerformancePeaceList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformancePeaceList 실행");
		return service.getPIP(requestDto).getPerformancePeaceList(); // performancePeaceList만 가져오기
	}
	// 테스트용(performanceStatusList 출력)
	@GetMapping("/getPerformanceStatusList")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public PerformanceStatusListDto getPerformanceStatusList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceStatusList 실행");
		return service.getPIP(requestDto).getPerformanceStatusList(); // performanceStatusList만 가져오기
	}
}
