package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.FavoritePostDTO;
import com.tl.mapper.FavoriteMapper;

import lombok.Setter;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Setter(onMethod_ = @Autowired)
	public FavoriteMapper mapper;

	public Integer checkFavorite(String memberId,Long perNum) {
		return mapper.checkFavorite(memberId, perNum);
	}
	
	public int handleFavorite(String memberId, Long perNum) {
		Integer existing= mapper.checkFavorite(memberId, perNum);
		
		if (existing == null) {
			mapper.addFavorite(memberId, perNum);
			return 1;
		} else {
			int newLiked = existing == 0 ? 1 : 0;
			mapper.handleFavorite(memberId, perNum,newLiked);
			return newLiked;
		}
	}

	public ArrayList<FavoritePostDTO> getFavoriteList(String memberId) {
		return mapper.getFavorite(memberId);
	}
	public int countFavorite(String memberId) {
		return mapper.countFavorite(memberId);
	}

}
