package com.tl.mapper;

import java.util.ArrayList;

import com.tl.dto.ReplyDto;
import com.tl.dto.ReplyRequestDto;

public interface ReplyMapper {

	public void write(ReplyDto reply);
	public ArrayList<ReplyDto> read(Long originNo);
	public void delete(ReplyRequestDto request);
}
