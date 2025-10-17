package com.tl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LikeResponse {
    private String memberId;
    private Long postNo;
    private int liked;
    private int totalLikes;
    private Long perNum;
}