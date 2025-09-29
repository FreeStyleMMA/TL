package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.FavoriteDTO;

public interface FavoriteMapper {

	public void addFavorite(@Param("memberId")String memberId,@Param("per_id")Long per_id);
	public ArrayList<FavoriteDTO> getFavorite(String memberId);
	public Integer checkFavorite(@Param("memberId") String memberId,@Param("per_id")Long per_id);
	public void handleFavorite(@Param("memberId")String memberId,@Param("per_id")Long per_id,@Param("newLiked") int newLiked);
}
