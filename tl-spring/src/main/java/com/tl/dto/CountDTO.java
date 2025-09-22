package com.tl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountDTO {
	public Long postNo;
	public int count;
}
