package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.FavoriteDTO;
import com.tl.mapper.FavoriteMapper;

import lombok.Setter;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Setter(onMethod_ = @Autowired)
	public FavoriteMapper mapper;

	// 공연 좋아요 테이블
	public FavoriteDTO checkFavorite(String memberId, Long per_id) {
		return mapper.checkFavorite(memberId, per_id);
	}

	public void handleFavorite(String memberId, Long per_id) {
		FavoriteDTO existing = mapper.checkFavorite(memberId, per_id);
		if (existing == null) {
			mapper.addFavorite(memberId, per_id);
		} else {
			int newLiked = existing.liked == 0 ? 1 : 0;
			mapper.handleFavorite(memberId, per_id, newLiked);
		}
	}

	public ArrayList<FavoriteDTO> getFavorite(String memberId) {
		return mapper.getFavorite(memberId);
	}

}
