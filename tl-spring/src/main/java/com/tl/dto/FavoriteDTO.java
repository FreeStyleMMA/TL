package com.tl.dto;

import java.util.Date;
import lombok.Data;

@Data
public class FavoriteDTO {
	public Long fav_id; // favorite 고유 key(no )
	public String memberId;
	public Long per_id;
	public Date create_at;
	public int liked; // 토글로 좋아요 상태 관리
}
