package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.FavoritePostDTO;

public interface FavoriteMapper {
	public void addFavorite(@Param("memberId")String memberId,@Param("perId")String perId);
	public ArrayList<FavoritePostDTO> getFavorite(String memberId);
	public Integer checkFavorite(@Param("memberId") String memberId,@Param("perId")String perId);
	public void handleFavorite(@Param("memberId")String memberId,
								@Param("perId")String perId,
								@Param("newLiked") int newLiked);
	public int countFavorite(String memberId);
}
