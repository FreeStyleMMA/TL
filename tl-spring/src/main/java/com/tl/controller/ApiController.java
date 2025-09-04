package com.tl.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;
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
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).performanceinfoList);

		return "tl/getPerformanceInfoTable";
	}

	// 호출 시 PerformanceInfoProcessor 반환(실제 사용할 반환용)
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(PerformanceRequestDto requestDto) {
		return service.getPIP(requestDto).performanceinfoList; // performanceInfoList만 가져오기
	}
}
