package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.FavoriteDTO;

public interface FavoriteMapper {

	public void addFavorite(@Param("memberId")String memberId,@Param("perId")String perId);
	public ArrayList<FavoriteDTO> getFavorite(String memberId);
	public Integer checkFavorite(@Param("memberId") String memberId,@Param("perId")String perId);
	public void handleFavorite(@Param("memberId")String memberId,@Param("perId")String perId,@Param("newLiked") int newLiked);
}
