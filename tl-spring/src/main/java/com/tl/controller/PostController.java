package com.tl.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.PostDto;
import com.tl.service.PostService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/post/*")
public class PostController {

	@Setter(onMethod_ = @Autowired)
	public PostService service;

// 	write는 post 객체 전체를 받아오기
	@GetMapping("/write")
	public void write(@RequestBody PostDto post) {
	}

//	read는 no만 받아와서 select 문으로 넘기기
	@GetMapping("/read")
	public ArrayList<PostDto> read(@RequestParam Long no) {
		return service.read(no);
	}
	
//	delete는 no만 받아와서 delete 문으로 넘기기
	@GetMapping("/delete")
	public void delete(@RequestParam Long no) {
	}

}
