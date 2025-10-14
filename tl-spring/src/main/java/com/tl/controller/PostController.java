package com.tl.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tl.dto.CountDTO;
import com.tl.dto.FreeBoardResponse;
import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.LikeResponse;
import com.tl.dto.MyPostResponse;
import com.tl.dto.PostDto;
import com.tl.service.PostService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/post/*")
@CrossOrigin(value = "http://localhost:3000")
public class PostController {

	@Setter(onMethod_ = @Autowired)
	public PostService service;

// 	write는 post 객체 전체를 받아오기	
	@PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public String write(@RequestPart("post") PostDto post, @RequestPart(value="media",required=false) MultipartFile media) throws Exception {
		log.info("포스팅 요청 도착"+post);

		if (media == null || media.isEmpty()) {
			System.out.println("첨부 파일 없음");
		} else {
			// 1. UUID 생성 + 확장자 추출
			String uuid = UUID.randomUUID().toString(); // 파일 저장을 위한 고유 문자열 생성.
			String originalFilename = media.getOriginalFilename(); // 원본 파일명 받아오기
			String ext = originalFilename.substring(originalFilename.lastIndexOf("."));// 확장자 추출
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
		}
			service.write(post);
		
		return "업로드 성공";
	}

	@GetMapping("/getReviewList") // 시작 no 받아와서 뒤에 4개?(한 페이지에 구현 할 갯수 정하고) 받아오기
	public ArrayList<PostDto> getReviewList(@RequestParam Long no) {
		return service.getReviewList(no);
	}

	@GetMapping("/getFreeList") // currentPage 번호 받아와서 몇 번 부터 몇 번 까지 보여줄지 글 postNo 계산해서 객체 리스트 반납
	public FreeBoardResponse getFreeList(@RequestParam int currentPage) {
		return FreeBoardResponse.builder()
				.postList(service.getFreeList(currentPage))
				.totalPages(service.getTotalFreePosts())
				.build();
	}
	
	@GetMapping("/getComHomeTop") //comhomepage 상단 좋아요 갯수가 가장 많은 post. post left join liked >> postMapper.xml 참고
	public ArrayList<PostDto> getComHomeTop() {
		return service.getComHomeTop();
	}
	

	@GetMapping("/read")//	read는 no만 받아와서 select 문으로 넘기기
	public PostDto read(@RequestParam Long no) {
		PostDto post = service.read(no);
		return post;
	}


	@DeleteMapping("/delete")//	delete는 no만 받아와서 delete 문으로 넘기기
	public void delete(@RequestParam Long no) {
		service.delete(no);
	}

	@PostMapping("/handleLike")//좋아요 토글(1 or 0) 상태 관리
	public LikeResponse handleLike(@RequestBody LikeRequest request) {
		int newLiked = service.handleLike(request);
		int totalLikes = service.countLikes(request.getPostNo());

		return LikeResponse.builder()
				.memberId(request.getMemberId())
				.postNo(request.getPostNo())
				.liked(newLiked)
				.totalLikes(totalLikes)
				.build();
	}

	@GetMapping("/countLikes") // 좋아요 누른 이후 숫자 계산 (+1 or -1)
	public int countLikes(@RequestParam Long postNo) {
		return service.countLikes(postNo);
	}

	@GetMapping("/initialLikes") // 첫 마운팅 시 좋아요 초기값(count) 반납
	public List<CountDTO> initialLikes(@RequestParam List<Long> postNos, @RequestParam String memberId) {
	    log.info("요청 멤버 아이디: " + memberId);
	    List<CountDTO> result = new ArrayList<>();
	    for (Long postNo : postNos) {
	        // 🔹 null 방어 코드
	        LikeDTO like = service.getLike(memberId, postNo);
	        int liked = (like != null) ? like.getLiked() : 0;
	        int count = service.countLikes(postNo);
	        result.add(CountDTO.builder()
	                .postNo(postNo)
	                .count(count)
	                .liked(liked)
	                .build());
	    }

	    return result;
	}
	
	@GetMapping("/getMyPost")
	public ArrayList<MyPostResponse> getMyPost(@RequestParam String memberId) {
		return service.getMyPost(memberId);
	}
	@GetMapping("/getHomePosts")
	public ArrayList<PostDto> getHomePosts(){
		return service.getHomePosts();
	}
}
