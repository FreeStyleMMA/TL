package com.tl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LikeDTO {
    private String memberId;
    private int postNo;
    private int liked;
    private int totalLikes;
}