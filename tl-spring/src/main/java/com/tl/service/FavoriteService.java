package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.FavoriteDTO;

public interface FavoriteService {

	public void handleFavorite(String memberId, String perId);
	public ArrayList<FavoriteDTO> getFavoriteList(String memberId);
	public Integer checkFavorite(String memberId,String perId);
	
}
