package com.tl.dto;

import lombok.Data;

@Data
public class LikeDTO {
	public String memberId;
	public Long postNo;
	public int liked;
}
