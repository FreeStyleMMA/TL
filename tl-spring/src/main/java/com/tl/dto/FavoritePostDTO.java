package com.tl.dto;

import java.util.Date;
import lombok.Data;

@Data
public class FavoritePostDTO {
	public Long favId; // favorite 고유 key(no )
	public String memberId;
	public String perId;
	public String perTitle;
	public String perPoster;
	public String perStartD;
	public String perEndD;
	public Date createdAt;
	public int liked; // 토글로 좋아요 상태 관리
}
