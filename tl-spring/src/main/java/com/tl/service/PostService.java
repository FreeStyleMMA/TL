package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.LikeRequest;
import com.tl.dto.PostDto;

public interface PostService {
	public void write(PostDto post);
	public ArrayList<PostDto> getReviewList(Long no);
	public ArrayList<PostDto> getFreeList(Long no);
	public PostDto read(Long no);
	public void delete(Long no);
	public Long getTotalPosts();
	public int handleLike( LikeRequest request);
	public int countLikes(Long postNo);	
}
