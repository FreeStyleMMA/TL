package com.tl.mapper;

import java.util.ArrayList;

import com.tl.dto.FavoriteDTO;

public interface FavoriteMapper {

	public FavoriteDTO checkFavorite(String memberId,Long per_id);
	public void handleFavorite(String memberId, Long per_id,int liked);
	public void addFavorite(String memberId,Long per_id);
	public ArrayList<FavoriteDTO> getFavorite(String memberId);
}
