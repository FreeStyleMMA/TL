package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.PerformanceInfoDto;
import com.tl.dto.PerformanceRequestDto;

public interface PerformanceDBService {
	public void addPerformance(ArrayList<PerformanceInfoDto> pfmInfo);
	public ArrayList<PerformanceInfoDto> getPerformance(PerformanceRequestDto request);
	public void fetchPerformance();
	public void resetPerformance();
	public String getUpdateDate();
}
