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

	// ȣ�� �� getPerformanceInfo.jsp�� �̵�(api ���� �׽�Ʈ��)
	@RequestMapping("/getPerformanceInfoTable")
	public String getPerformanceInfoTable(PerformanceRequestDto requestDto, Model model) {
		// performanceInfoList ����
		model.addAttribute("performanceInfoList", service.getPIP(requestDto).performanceinfoList);

		return "tl/getPerformanceInfoTable";
	}

	// ȣ�� �� PerformanceInfoProcessor ��ȯ(���� ����� ��ȯ��)
	@GetMapping(value = "/getPerformanceInfo", produces = "application/json")
	@ResponseBody
	public ArrayList<PerformanceInfoDto> getPerformanceInfo(PerformanceRequestDto requestDto) {
		return service.getPIP(requestDto).performanceinfoList; // performanceInfoList�� ��������
	}
}
