package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.PostDto;
import com.tl.mapper.PostMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class PostServiceImpl implements PostService{
	
	@Setter(onMethod_=@Autowired)
	public PostMapper mapper;
	
	public void write(PostDto post) {}
	
	public ArrayList<PostDto> read(Long no){
		return mapper.read(no);
	}
	
	public void delete(Long no) {}
	

	
}
