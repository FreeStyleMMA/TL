package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.PostDto;

public interface PostService {
	public void write(PostDto post);
	public ArrayList<PostDto> read(Long no);
	public void delete(Long no);

}
