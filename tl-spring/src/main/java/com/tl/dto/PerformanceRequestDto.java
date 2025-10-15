package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceRequestDto { // api에서 공연 목록을 받아올 때 요청할 매개변수를 담을 클래스
    private String startdate = "";     // 공연 시작일자 (필수, 8자리, 예: 20230101)
    private String enddate = "";       // 공연 종료일자 (필수, 8자리, 예: 20230630)
    private Integer cpage = 0;         // 현재 페이지 (필수, 최대 3자리, 예: 1)
    private Integer rows = 0;          // 페이지당 목록 수 (필수, 최대 3자리, 최대 100건, 예: 10)
    private String shprfnm = "";       // 공연명 (선택, 최대 100자, URL Encoding 필요, 예: 사랑)
    private String shprfnmfct = "";    // 공연시설명 (선택, 최대 100자, URL Encoding 필요, 예: 예술의전당)
    private String shcate = "";        // 장르코드 (선택, 최대 4자리, 예: AAAA)
    private String prfplccd = "";      // 공연장 코드 (선택, 최대 4자리, 예: FC000001-01)
    private String signgucode = "";    // 지역(시도) 코드 (선택, 2자리, 예: 11)
    private String signgucodesub = ""; // 지역(구군) 코드 (선택, 4자리, 예: 1111)
    private String kidstate = "";      // 아동공연 여부 (선택, 1자리, Y/N, 지정 안하면 전체공연)
    private String prfstate = "";      // 공연상태 코드 (선택, 2자리, 예: 01)
    private String openrun = "";       // 오픈런 여부 (선택, 2자리, Y/N)
    private String afterdate = "";     // 해당일자 이후 등록/수정된 항목만 출력 (선택, 8자리, 예: 20230101)
    private String perRequestT = "";   // 추천 공연 여부(DB)
    private Integer startIndex = 0; // 시작 인덱스(DB)
    
    public PerformanceRequestDto(String startdate, String enddate, String shcate, String perRequestT){
    	this.startdate = startdate;
    	this.enddate = enddate;
    	this.shcate = shcate;
    	this.perRequestT = perRequestT;
    }
    public PerformanceRequestDto(String startdate, String enddate, Integer cpage, Integer rows, String shcate){
    	this.startdate = startdate;
    	this.enddate = enddate;
    	this.cpage = cpage;
    	this.rows = rows;
    	this.shcate = shcate;
    }
    public PerformanceRequestDto() {
    }

	public void setCpage(Integer cpage) {
        this.cpage = cpage;
        this.startIndex = (this.cpage - 1) * this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
        this.startIndex = (this.cpage - 1) * this.rows;
    }
}


