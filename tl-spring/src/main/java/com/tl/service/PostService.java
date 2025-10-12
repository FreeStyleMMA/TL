package com.tl.service;

import java.util.ArrayList;

import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.MyPostResponse;
import com.tl.dto.PostDto;

public interface PostService {
	public void write(PostDto post);
	public ArrayList<PostDto> getReviewList(Long no);
	public ArrayList<PostDto> getFreeList(int currentPage);
	public ArrayList<PostDto> getComHomeTop();
	public PostDto read(Long no);
	public void delete(Long no);
	public int getTotalFreePosts();
	public int handleLike( LikeRequest request);
	public int countLikes(Long postNo);	
	public LikeDTO getLike(String memberId, Long postNo);
	public ArrayList<MyPostResponse> getMyPost(String memberId);
	public ArrayList<PostDto> getHomePosts();
}
