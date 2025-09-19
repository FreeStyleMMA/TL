package com.tl.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.ReplyDto;
import com.tl.dto.ReplyRequestDto;
import com.tl.service.ReplyService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/reply/*")
public class ReplyController {
	@Setter(onMethod_ = @Autowired)
	public ReplyService service;

	// 댓글 작성 파트
	@PostMapping("/write")
	public void write(@RequestBody ReplyDto reply) {
		service.write(reply);

	}

	// 댓글 읽기. List 형식으로 데이터 전송.
	@GetMapping("/read")
	public ArrayList<ReplyDto> read(@RequestParam Long originNo) {
		return service.read(originNo);
	}

	@GetMapping("/delete")
	public void delete(@ModelAttribute ReplyRequestDto request) {
		service.delete(request);
	}

	@GetMapping("/count")
	public int getTotalReplys(@RequestParam Long originNo) {
		log.info("댓글 갯수 요청 도착");
		return service.getTotalReplys(originNo);
	}
}
