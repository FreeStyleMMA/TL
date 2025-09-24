package com.tl.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeBoardResponse {
	public List<PostDto> postList;
	public int totalPages;
}
