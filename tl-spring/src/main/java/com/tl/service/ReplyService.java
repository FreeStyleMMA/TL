package com.tl.service;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestParam;

import com.tl.dto.ReplyDto;
import com.tl.dto.ReplyRequestDto;

public interface ReplyService {
	public void write(ReplyDto reply);
	public ArrayList<ReplyDto> read(Long originNo);
	public void delete(ReplyRequestDto request);
	public int getTotalReplys(Long originNo);

}
