package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.MyRepliesResponse;
import com.tl.dto.ReplyDto;

public interface ReplyMapper {

	public void write(ReplyDto reply);
	public ArrayList<ReplyDto> read(Long originNo);
	public void delete(@Param("no") Long no, @Param("originNo") Long originNo);
	public int getTotalReplys(Long originNo);
	public ArrayList<MyRepliesResponse> getMyReplies(String memberId);
}
