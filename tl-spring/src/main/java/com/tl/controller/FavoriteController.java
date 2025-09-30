package com.tl.controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.FavoriteDTO;
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
	public int handleFavorite(@RequestParam String memberId, @RequestParam String perId) {
		log.info("핸들 요청 도착0");
		service.handleFavorite(memberId,perId);
		return service.checkFavorite(memberId,perId);
	}
	
	@GetMapping("checkFavorite")// favorite테이블 liked 확인 (1 or 0)
	public Integer checkFavorite(@RequestParam String memberId,@RequestParam String perId) {
		return service.checkFavorite(memberId, perId);
	}
	@GetMapping("getFavorite")
	public ArrayList<FavoriteDTO> getFavorite(@RequestParam String memberId) {
		return service.getFavoriteList(memberId);
	}
	
}
	