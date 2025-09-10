package com.tl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 클래스에 정의되지 않은 필드가 xml에 있어도 무시
public class PerformancePeaceDto { // api에서 받아온 공연시설 상세 정보를 담을 클래스
    private String fcltynm;       // 공연시설명
    private String mt10id;        // 공연시설ID
    private String mt13cnt;       // 공연장 수
    private String fcltychartr;   // 시설특성
    private String opende;        // 개관연도
    private String seatscale;     // 객석 수
    private String telno;         // 전화번호
    private String relateurl;     // 홈페이지
    private String adres;         // 주소
    private String la;            // 위도
    private String lo;            // 경도
    private String restaurant;    // 레스토랑 여부
    private String cafe;          // 카페 여부
    private String store;         // 편의점 여부
    private String nolibang;      // 놀이방 여부
    private String suyu;          // 수유실 여부
    private String parkbarrier;   // 장애시설-주차장
    private String restbarrier;   // 장애시설-화장실
    private String runwbarrier;   // 장애시설-경사로
    private String elevbarrier;   // 장애시설-엘리베이터
    private String parkinglot;    // 주차시설

    @JacksonXmlElementWrapper(localName = "mt13s")
    @JacksonXmlProperty(localName = "mt13")
    private List<StageDto> mt13s;   // 공연장 목록
}
