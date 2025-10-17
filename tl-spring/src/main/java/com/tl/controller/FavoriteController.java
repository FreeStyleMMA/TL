package com.tl.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.FavoritePostDTO;
import com.tl.dto.LikeResponse;
import com.tl.service.FavoriteService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
@RestController
@Log4j
@RequestMapping("/favorite/*")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class FavoriteController {
	
	@Setter(onMethod_ = @Autowired)
	public FavoriteService service;
	
	@PostMapping("handleFavorite")
	public LikeResponse handleFavorite(@RequestParam String memberId, @RequestParam Long perNum) {
		int newLiked = service.handleFavorite(memberId,perNum);
		int totalFavorite = service.countFavorite(memberId);
		return LikeResponse.builder()
				.memberId(memberId)
				.perNum(perNum)
				.liked(newLiked)
				.totalLikes(totalFavorite)
				.build();
	}
	
	@GetMapping("checkFavorite")// favorite테이블 liked 확인 (1 or 0)
	public Integer checkFavorite(@RequestParam String memberId,@RequestParam Long perNum) {
		  if (perNum == null) {
			    // 예외 처리 또는 기본 동작
			    throw new IllegalArgumentException("perNum 파라미터가 필요합니다.");
			  }
			  return service.checkFavorite(memberId, perNum);
			}
	@GetMapping("getFavorite")
	public ArrayList<FavoritePostDTO> getFavorite(@RequestParam String memberId) {
		return service.getFavoriteList(memberId);
	}
	@GetMapping("countFavorite")
	public int countFavorite(@RequestParam String memberId) {
		return service.countFavorite(memberId);
	}
	
}
	