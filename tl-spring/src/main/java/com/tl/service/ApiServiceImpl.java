package com.tl.service;

import org.springframework.stereotype.Service;

import com.tl.dto.PerformanceRequestDto;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ApiServiceImpl implements ApiService{
	
	@Override
	public PerformanceInfoProcessor getPIP(PerformanceRequestDto prd) { // ���� ���� ��ȯ
		PerformanceInfoProcessor pip = new PerformanceInfoProcessor(prd); 
			
		return pip;
	}
}
