package com.tl.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.ReplyDto;
import com.tl.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/reply/*")
public class ReplyController {
	@Setter(onMethod_ = @Autowired)
	public ReplyMapper mapper;

//	Dto 중에 memberId는 따로 받아야돼서 쪼개서 받아야 할 가능성. 
	@GetMapping("/write")
	public void write(@RequestBody ReplyDto reply) {

	}

	
	@GetMapping("/read")
	public ArrayList<ReplyDto> read(@RequestParam long no, @RequestParam long originNo) {
		return mapper.read(no,originNo);
	}

	@GetMapping("/delete")
	public void delete(@RequestParam long no, @RequestParam long originNo) {

	}

}
