package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.FavoriteDTO;

public interface FavoriteService {

	public void handleFavorite(String memberId, Long per_id);
	public ArrayList<FavoriteDTO> getFavoriteList(String memberId);
	public Integer checkFavorite(String memberId,Long per_id);
	
}
