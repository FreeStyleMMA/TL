package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.ReplyDto;

public interface ReplyService {
	public void write(ReplyDto reply);
	public ArrayList<ReplyDto> read(long no, long originNo);
	public void delete(long no, long originNo);

}
