package com.tl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.CountDTO;
import com.tl.dto.MyRepliesResponse;
import com.tl.dto.ReplyDto;
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

	@DeleteMapping("/delete")
	public void delete(@RequestParam("no") Long no,@RequestParam("originNo") Long originNo) {
		log.info("삭제 요청 도착");
		service.delete(no,originNo);
	}

	@GetMapping("/count")
	public int getTotalReplys(@RequestParam Long originNo) {
		return service.getTotalReplys(originNo);
	}
	@GetMapping("/initialReplies")
	public List<CountDTO> initialReplies(@RequestParam List<Long> postNos) {
	     List<CountDTO> result = new ArrayList<>();
	        for (Long postNo : postNos) {
	            int count = service.getTotalReplys(postNo);
	            result.add(CountDTO.builder()
	                    .postNo(postNo)
	                    .count(count)
	                    .build());
	        }
	        log.info("initialReplies"+result);
	        return result; 
	}
	@GetMapping("/getMyReplies")
	public ArrayList<MyRepliesResponse> getMyReplies(@RequestParam String memberId){
		return service.getMyReplies(memberId);
	}
}
