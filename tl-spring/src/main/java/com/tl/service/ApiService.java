package com.tl.service;

import com.tl.dto.PerformanceRequestDto;

public interface ApiService {
	public PerformanceInfoProcessor getPIP(PerformanceRequestDto prd); // 공연 정보 반환
}
