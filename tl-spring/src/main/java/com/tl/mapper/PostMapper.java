package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.PostDto;

public interface PostMapper {
	public void write(PostDto post);
	public ArrayList<PostDto> getList( @Param("lastNo")Long no,@Param("pageSize")int PAGE_SIZE);
	public PostDto read(Long no);
	public void delete(Long no);
	public int getTotalPosts();
	
}
