package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.MyRepliesResponse;
import com.tl.dto.ReplyDto;

public interface ReplyService {
	public void write(ReplyDto reply);
	public ArrayList<ReplyDto> read(Long originNo);
	public void delete(Long no,Long originNo);
	public int getTotalReplys(Long originNo);
	public ArrayList<MyRepliesResponse> getMyReplies(String memberId);

}
