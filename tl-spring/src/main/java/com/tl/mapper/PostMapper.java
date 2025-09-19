package com.tl.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.PostDto;

public interface PostMapper {
	public void write(PostDto post);
	public ArrayList<PostDto> getList( @Param("lastNo")long no,@Param("pageSize")int PAGE_SIZE);
	public PostDto read(long no);
	public void delete(long no);
	public int getTotalPosts();
	public int handleLike( @Param("memberId")String memberId,@Param("liked")int liked, @Param("postNo")int postNo);
	public LikeDTO getLike(@Param("memberId")String memberId,@Param("postNo")int postNo);
	public int addLike(LikeRequest request);
	public int countLikes(int postNo);
}
