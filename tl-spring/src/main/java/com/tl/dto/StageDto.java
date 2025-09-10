package com.tl.dto;

import lombok.Data;

@Data
public class StageDto {
    private String prfplcnm;           // 공연장명
    private String mt13id;             // 공연장 ID
    private String seatscale;          // 좌석규모
    private String stageorchat;        // 무대시설_오케스트라피트
    private String stagepracat;        // 무대시설_연습실
    private String stagedresat;        // 무대시설_분장실
    private String stageoutdrat;       // 무대시설_야외공연장
    private String disabledseatscale;  // 장애인시설_관객석
    private String stagearea;          // 무대시설_무대넓이
}
