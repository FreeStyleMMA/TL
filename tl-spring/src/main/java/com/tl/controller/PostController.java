package com.tl.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.PostDto;
import com.tl.service.PostService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/post/*")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")

public class PostController {

	@Setter(onMethod_ = @Autowired)
	public PostService service;

//	private static final int PAGE_SIZE = 4;
	
// 	write는 post 객체 전체를 받아오기	
	@PostMapping(value="/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String write(@RequestPart("post") PostDto post,
			@RequestPart("media") MultipartFile media) throws Exception {
		log.info("포스팅 요청 도착");		
		// 1. UUID 생성 + 확장자 추출
		String uuid = UUID.randomUUID().toString(); // 파일 저장을 위한 고유 문자열 생성.
		String originalFilename = media.getOriginalFilename(); // 원본 파일명 받아오기
		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));//확장자 추출
		String savedFileName = uuid + ext; // uuid + 확장자로 파일 저장.

		// 2. 저장 경로 (서버 내 폴더)
		String saveDir = "C:/upload/";
		File saveFile = new File(saveDir + savedFileName); // 저장 폴더+ 저장 파일명으로 파일 저장 준비.

		// 3. 폴더 없으면 생성
		if (!saveFile.getParentFile().exists()) {
		    saveFile.getParentFile().mkdirs();
		}
		// 4. 파일 저장
		media.transferTo(saveFile);
		// 5. 이미지 경로 세팅 (DB 저장용)
		post.setMedia("/upload/" + savedFileName);
		
		service.write(post);
		
		return "업로드 성공";
	}
	
	
	@GetMapping("/getList") // 시작 no 받아와서 뒤에 3개?(한 페이지에 구현 할 갯수 정하고) 받아오기
	public ArrayList<PostDto> getList(@RequestParam long no) {
		return service.getList(no);
	}

//	read는 no만 받아와서 select 문으로 넘기기
	@GetMapping("/read")
	public PostDto read(@RequestParam long no) { 
		PostDto post =  service.read(no);
		return post;
	}
	
//	delete는 no만 받아와서 delete 문으로 넘기기
	@GetMapping("/delete")
	public void delete(@RequestParam long no) {
	service.delete(no);
	}
	
	@PostMapping("/handleLike")
	public LikeDTO handleLike(@RequestBody LikeRequest request) {
		log.info("좋아요 요청 도착"+request);
		int newLiked=service.handleLike(request);
		int totalLikes = service.countLikes(request.getPostNo());
		
		return LikeDTO.builder()
				.memberId(request.getMemberId())
				.postNo(request.getPostNo())
				.liked(newLiked)
				.totalLikes(totalLikes)
				.build();
	}
	
	@GetMapping("/countLikes")
	public int countLikes(@RequestParam int postNo) {
		return service.countLikes(postNo);
	}
	
	

}
