package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.FavoritePostDTO;

public interface FavoriteService {

	public int handleFavorite(String memberId, String perId);
	public ArrayList<FavoritePostDTO> getFavoriteList(String memberId);
	public Integer checkFavorite(String memberId,String perId);
	public int countFavorite(String memberId);
}
