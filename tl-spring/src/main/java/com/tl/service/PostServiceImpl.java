package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tl.dto.PostDto;
import com.tl.mapper.PostMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j

public class PostServiceImpl implements PostService{
	
	@Setter(onMethod_=@Autowired)
	public PostMapper mapper;
	
	private static final int PAGE_SIZE = 4; // 한 페이지에 들어갈 게시물 수
	
	public void write(PostDto post) {
		mapper.write(post);
	}
	
	//페이지에 보여줄 리스트 처리
	public ArrayList<PostDto> getList(Long no) {
		 return mapper.getList(no,PAGE_SIZE);
	}
	
	
//	DB에서 총 게시물 갯수 받아오기
	public int getTotalPosts() {
		int totalPosts = mapper.getTotalPosts();
		return totalPosts;
	}
//		
	public PostDto read(@RequestParam Long no) {
		return mapper.read(no);
	}
	
	public void delete(Long no) {
		mapper.delete(no);
	}
	
}
