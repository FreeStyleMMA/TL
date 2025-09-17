package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.ReplyDto;
import com.tl.dto.ReplyRequestDto;
import com.tl.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

	@Setter(onMethod_ = @Autowired)
	public ReplyMapper mapper;

	public void write(ReplyDto reply) {
		mapper.write(reply);
	}

	public ArrayList<ReplyDto> read(Long originNo) {
		return mapper.read(originNo);
	}

	public void delete(ReplyRequestDto request) {
		mapper.delete(request);
	}

}
