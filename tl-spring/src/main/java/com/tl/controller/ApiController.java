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

	// 호출 시 PerformanceInfoProcessor 반환(실제 사용할 반환용)

	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react의 매개변수 요청을 자동으로 바꿔주기 위해 ModelAttribute 사용
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {

		return dbService.getPerformance(requestDto);
	}

	// �׽�Ʈ��(DB ������Ʈ)
	@RequestMapping("/fetchDB")
	public String fetchDB() {
		scheService.DBFetchTest();

		return "redirect:/home";
	}

	// �����ڿ�(DB �ʱ�ȭ)
	@RequestMapping("/resetDB")
	public String resetDB() {
		scheService.resetPerformances();

		return "redirect:/home";
	}

	@GetMapping(value = "/getPerformanceInfoApi", produces = "application/json")
	@ResponseBody
	// �׽�Ʈ��(performanceInfoList ���)
	public ArrayList<PerformanceInfoDto> getPerformanceInfoApi(@ModelAttribute PerformanceRequestDto requestDto) {
		return service.getPIP(requestDto).getPerformanceInfoList();
	}

	// �׽�Ʈ��(ȣ�� �� getPerformanceInfo.jsp�� �̵�)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList ����
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).getPerformanceInfoList());

		return "tl/getPerformanceInfoTable";
	}

	// �׽�Ʈ��(performanceList ���)
	@GetMapping("/getPerformanceList")
	@ResponseBody
	public PerformanceListDto getPerformanceList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList ����");
		return service.getPIP(requestDto).getPerformanceList(); // performanceList�� ��������
	}

	// �׽�Ʈ��(performanceDetailList ���)
	@GetMapping("/getPerformanceDetailList")
	@ResponseBody
	public ArrayList<PerformanceDetailDto> getPerformanceDetailList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceList ����");
		return service.getPIP(requestDto).getPerformanceDetailList(); // performanceDetailList�� ��������
	}

	// �׽�Ʈ��(performancePeaceList ���)
	@GetMapping("/getPerformancePeaceList")
	@ResponseBody
	public ArrayList<PerformancePeaceDto> getPerformancePeaceList(PerformanceRequestDto requestDto) {
		log.info("getPerformancePeaceList ����");
		return service.getPIP(requestDto).getPerformancePeaceList(); // performancePeaceList�� ��������
	}

	// �׽�Ʈ��(performanceStatusList ���)
	@GetMapping("/getPerformanceStatusList")
	@ResponseBody
	public PerformanceStatusListDto getPerformanceStatusList(PerformanceRequestDto requestDto) {
		log.info("getPerformanceStatusList ����");
		return service.getPIP(requestDto).getPerformanceStatusList(); // performanceStatusList�� ��������
	}

}
