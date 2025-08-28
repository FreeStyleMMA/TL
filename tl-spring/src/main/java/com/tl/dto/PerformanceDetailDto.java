package com.tl.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 클래스에 정의되지 않은 필드가 xml에 있어도 무시
public class PerformanceDetailDto {

    public String mt20id;        // 공연ID
    public String prfnm;         // 공연명
    public String prfpdfrom;     // 공연시작일
    public String prfpdto;       // 공연종료일
    public String fcltynm;       // 공연시설명(공연장명)
    public String prfcast;       // 공연출연진
    public String prfcrew;       // 공연제작진
    public String prfruntime;    // 공연 런타임
    public String prfage;        // 공연 관람 연령
    public String entrpsnm;      // 기획제작사
    public String entrpsnmP;     // 제작사
    public String entrpsnmA;     // 기획사
    public String entrpsnmH;     // 주최
    public String entrpsnmS;     // 주관
    public String pcseguidance;  // 티켓 가격
    public String poster;        // 포스터 이미지 경로
    public String sty;           // 줄거리
    public String area;          // 지역
    public String genrenm;       // 장르
    public String openrun;       // 오픈런 여부 (Y/N)
    public String visit;         // 내한 여부 (Y/N)
    public String child;         // 아동 공연 여부 (Y/N)
    public String daehakro;      // 대학로 공연 여부 (Y/N)
    public String festival;      // 축제 여부 (Y/N)
    public String musicallicense;// 뮤지컬 라이센스 여부 (Y/N)
    public String musicalcreate; // 뮤지컬 창작 여부 (Y/N)
    public String updatedate;    // 최종 수정일
    public String prfstate;      // 공연 상태
    public String mt10id;        // 공연시설ID
    public String dtguidance;    // 공연 시간 안내

    @JacksonXmlElementWrapper(localName = "styurls") // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
    @JacksonXmlProperty(localName = "styurl")        // XML에서 이 이름으로 매핑
    private ArrayList<String> styurls;               // 소개 이미지 목록

    @JacksonXmlElementWrapper(localName = "relates") // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
    @JacksonXmlProperty(localName = "relate")       // XML에서 이 이름으로 매핑
    private ArrayList<RelateDto> relates;           // 예매처 목록
}
