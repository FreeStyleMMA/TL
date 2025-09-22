package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.PostDto;

public interface PostMapper {
	public void write(PostDto post);
	public ArrayList<PostDto> getReviewList( @Param("lastNo")Long no,@Param("pageSize")int PAGE_SIZE);
	public ArrayList<PostDto> getFreeList( @Param("lastNo")Long no,@Param("pageSize")int PAGE_SIZE);
	public PostDto read(Long no);
	public void delete(Long no);
	public Long getTotalPosts();
	public int handleLike( @Param("memberId")String memberId,@Param("liked")int liked, @Param("postNo")Long postNo);
	public LikeDTO getLike(@Param("memberId")String memberId,@Param("postNo")Long postNo);
	public int addLike(LikeRequest request);
	public int countLikes(Long postNo);
}
