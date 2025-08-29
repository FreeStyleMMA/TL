package com.tl.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 클래스에 정의되지 않은 필드가 xml에 있어도 무시
public class PerformanceDetailDto { // api에서 받아온 공연 상세 정보를 담을 클래스

    private String mt20id;        // 공연ID
    private String prfnm;         // 공연명
    private String prfpdfrom;     // 공연시작일
    private String prfpdto;       // 공연종료일
    private String fcltynm;       // 공연시설명(공연장명)
    private String prfcast;       // 공연출연진
    private String prfcrew;       // 공연제작진
    private String prfruntime;    // 공연 런타임
    private String prfage;        // 공연 관람 연령
    private String entrpsnm;      // 기획제작사
    private String entrpsnmP;     // 제작사
    private String entrpsnmA;     // 기획사
    private String entrpsnmH;     // 주최
    private String entrpsnmS;     // 주관
    private String pcseguidance;  // 티켓 가격
    private String poster;        // 포스터 이미지 경로
    private String sty;           // 줄거리
    private String area;          // 지역
    private String genrenm;       // 장르
    private String openrun;       // 오픈런 여부 (Y/N)
    private String visit;         // 내한 여부 (Y/N)
    private String child;         // 아동 공연 여부 (Y/N)
    private String daehakro;      // 대학로 공연 여부 (Y/N)
    private String festival;      // 축제 여부 (Y/N)
    private String musicallicense;// 뮤지컬 라이센스 여부 (Y/N)
    private String musicalcreate; // 뮤지컬 창작 여부 (Y/N)
    private String updatedate;    // 최종 수정일
    private String prfstate;      // 공연 상태
    private String mt10id;        // 공연시설ID
    private String dtguidance;    // 공연 시간 안내

    @JacksonXmlElementWrapper(localName = "styurls") // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
    @JacksonXmlProperty(localName = "styurl")        // XML에서 이 이름으로 매핑
    private ArrayList<String> styurls;               // 소개 이미지 목록

    @JacksonXmlElementWrapper(localName = "relates") // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
    @JacksonXmlProperty(localName = "relate")       // XML에서 이 이름으로 매핑
    private ArrayList<RelateDto> relates;           // 예매처 목록
}
