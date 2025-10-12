package com.tl.dto;

import lombok.Data;

@Data
public class FavoriteDTO {
	public String memberId;
	public String perId;
	public int liked; // 토글로 좋아요 상태 관리
}
	