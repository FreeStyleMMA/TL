package com.tl.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.tl.service.TicketService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@AllArgsConstructor
@RequestMapping("/tl/*")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class ApiController {

	ApiService service;
	TicketService dbService;

	// 호출 시 PerformanceInfoProcessor 반환(실제 사용할 반환용)
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {
		ArrayList<PerformanceInfoDto> pfmList = new ArrayList<PerformanceInfoDto>();
		// db에서 찾아보고 조건에 부합하는 데이터가 없거나 부족할 시 api에서 호출 받아오기
		pfmList = service.getPIP(requestDto).performanceInfoList;
//			dbService.addPerformance(pfmList);	
		log.info("api 받아온 데이터: " + pfmList);
		return pfmList;
	}

	// 호출 시 getPerformanceInfo.jsp로 이동(api 정보 테스트용)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList 전달
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).performanceInfoList);

		return "tl/getPerformanceInfoTable";
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

	@GetMapping("getHomePerform")
	@ResponseBody
	public ArrayList<PerformanceInfoDto> getHomePerform() {
		log.info("홈 공연"+dbService.getHomePerform());
		return dbService.getHomePerform();
	}
}