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
	// 깃 주석 테스트
	// 깃 주석 테스트
	// 새로운 깃 주석 테스트

	ApiService service;

	// ȣ�� �� getPerformanceInfo.jsp�� �̵�(api ���� �׽�Ʈ��)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList ����
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).performanceInfoList);

		return "tl/getPerformanceInfoTable";
	}

	// ȣ�� �� PerformanceInfoProcessor ��ȯ(���� ����� ��ȯ��)
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	// react�� �Ű����� ��û�� �ڵ����� �ٲ��ֱ� ���� ModelAttribute ���
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceInfo ����");
		return service.getPIP(requestDto).performanceInfoList; // performanceInfoList�� ��������
	}
	
	
	// �׽�Ʈ��(performanceList ���)
	@GetMapping("/getPerformanceList")
	@ResponseBody
	// react�� �Ű����� ��û�� �ڵ����� �ٲ��ֱ� ���� ModelAttribute ���
	public PerformanceListDto getPerformanceList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceList ����");
		return service.getPIP(requestDto).getPerformanceList(); // performanceList�� ��������
	}
	// �׽�Ʈ��(performanceDetailList ���)
	@GetMapping("/getPerformanceDetailList")
	@ResponseBody
	// react�� �Ű����� ��û�� �ڵ����� �ٲ��ֱ� ���� ModelAttribute ���
	public ArrayList<PerformanceDetailDto> getPerformanceDetailList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceList ����");
		return service.getPIP(requestDto).getPerformanceDetailList(); // performanceDetailList�� ��������
	}
	// �׽�Ʈ��(performancePeaceList ���)
	@GetMapping("/getPerformancePeaceList")
	@ResponseBody
	// react�� �Ű����� ��û�� �ڵ����� �ٲ��ֱ� ���� ModelAttribute ���
	public ArrayList<PerformancePeaceDto> getPerformancePeaceList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformancePeaceList ����");
		return service.getPIP(requestDto).getPerformancePeaceList(); // performancePeaceList�� ��������
	}
	// �׽�Ʈ��(performanceStatusList ���)
	@GetMapping("/getPerformanceStatusList")
	@ResponseBody
	// react�� �Ű����� ��û�� �ڵ����� �ٲ��ֱ� ���� ModelAttribute ���
	public PerformanceStatusListDto getPerformanceStatusList(@ModelAttribute PerformanceRequestDto requestDto) {
		log.info("getPerformanceStatusList ����");
		return service.getPIP(requestDto).getPerformanceStatusList(); // performanceStatusList�� ��������
	}
}
