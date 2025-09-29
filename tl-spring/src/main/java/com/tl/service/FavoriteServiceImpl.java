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

	public Integer checkFavorite(String memberId, Long per_id) {
		return mapper.checkFavorite(memberId, per_id);
	}
	
	public void handleFavorite(String memberId, Long per_id) {
		Integer existing= mapper.checkFavorite(memberId, per_id);
		
		if (existing == null) {
			mapper.addFavorite(memberId, per_id);
		} else {
			int newLiked = existing == 0 ? 1 : 0;
			mapper.handleFavorite(memberId, per_id,newLiked);
		}
	}

	public ArrayList<FavoriteDTO> getFavoriteList(String memberId) {
		return mapper.getFavorite(memberId);
	}

}
