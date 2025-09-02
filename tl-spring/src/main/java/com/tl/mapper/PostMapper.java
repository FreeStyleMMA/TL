package com.tl.mapper;

import java.util.ArrayList;

import com.tl.dto.PostDto;

public interface PostMapper {
	public void write(PostDto post);
	public ArrayList<PostDto> read(Long no);
	public void delete(Long no);
}
