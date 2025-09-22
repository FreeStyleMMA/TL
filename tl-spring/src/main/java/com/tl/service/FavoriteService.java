package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.FavoriteDTO;

public interface FavoriteService {

	public FavoriteDTO checkFavorite(String memberId,Long per_id);
	public void handleFavorite(String memberId, Long per_id);
	public ArrayList<FavoriteDTO> getFavorite(String memberId);
	
}
