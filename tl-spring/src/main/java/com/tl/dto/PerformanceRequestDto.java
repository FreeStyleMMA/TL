package com.tl.dto;

import lombok.Data;

@Data
public class PerformanceRequestDto {
    public String startdate;     // 공연 시작일자 (필수, 8자리, 예: 20230101)
    public String enddate;       // 공연 종료일자 (필수, 8자리, 예: 20230630)
    public String cpage;         // 현재 페이지 (필수, 최대 3자리, 예: 1)
    public String rows;          // 페이지당 목록 수 (필수, 최대 3자리, 최대 100건, 예: 10)
    public String shprfnm;       // 공연명 (선택, 최대 100자, URL Encoding 필요, 예: 사랑)
    public String shprfnmfct;    // 공연시설명 (선택, 최대 100자, URL Encoding 필요, 예: 예술의전당)
    public String shcate;        // 장르코드 (선택, 최대 4자리, 예: AAAA)
    public String prfplccd;      // 공연장 코드 (선택, 최대 4자리, 예: FC000001-01)
    public String signgucode;    // 지역(시도) 코드 (선택, 2자리, 예: 11)
    public String signgucodesub; // 지역(구군) 코드 (선택, 4자리, 예: 1111)
    public String kidstate;      // 아동공연 여부 (선택, 1자리, Y/N, 지정 안하면 전체공연)
    public String prfstate;      // 공연상태 코드 (선택, 2자리, 예: 01)
    public String openrun;       // 오픈런 여부 (선택, 2자리, Y/N)
    public String afterdate;     // 해당일자 이후 등록/수정된 항목만 출력 (선택, 8자리, 예: 20230101)
}

