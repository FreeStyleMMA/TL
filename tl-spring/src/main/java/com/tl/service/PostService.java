package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.LikeRequest;
import com.tl.dto.PostDto;

public interface PostService {
	public void write(PostDto post);
	public ArrayList<PostDto> getList(long no);
	public PostDto read(long no);
	public void delete(long no);
	public int getTotalPosts();
	public int handleLike( LikeRequest request);
	public int countLikes(int postNo);	
}
