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

	public Integer checkFavorite(String memberId, String perId) {
		return mapper.checkFavorite(memberId, perId);
	}
	
	public int handleFavorite(String memberId, String perId) {
		Integer existing= mapper.checkFavorite(memberId, perId);
		
		if (existing == null) {
			mapper.addFavorite(memberId, perId);
			return 1;
		} else {
			int newLiked = existing == 0 ? 1 : 0;
			mapper.handleFavorite(memberId, perId,newLiked);
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
