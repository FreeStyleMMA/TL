package com.tl.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tl.dto.LikeDTO;
import com.tl.dto.LikeRequest;
import com.tl.dto.MyPostResponse;
import com.tl.dto.PostDto;
import com.tl.mapper.PostMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j

public class PostServiceImpl implements PostService {

	@Setter(onMethod_ = @Autowired)
	public PostMapper mapper;

	private static final int PAGE_SIZE_REVIEW = 4; // 리뷰게시판 페이지에 들어갈 게시물 수
	private static final int PAGE_SIZE_FREE = 10; // 자유게시판 한 페이지 당 게시물 수

	public void write(PostDto post) {
		mapper.write(post);
	}

	// 페이지에 보여줄 리스트 처리
	public ArrayList<PostDto> getReviewList(Long no) {
		return mapper.getReviewList(no, PAGE_SIZE_REVIEW);
	}

	// 페이지에 보여줄 리스트 처리
	public ArrayList<PostDto> getFreeList(int currentPage) {
		int offset = (currentPage - 1) * PAGE_SIZE_FREE;
		return mapper.getFreeList(PAGE_SIZE_FREE, offset);
	}

//	DB에서 총 게시물 갯수 받아오기
	public int getTotalFreePosts() {
		return mapper.getTotalFreePosts();

	}

	public ArrayList<PostDto> getComHomeTop() {
		return mapper.getComHomeTop();
	}

//		
	public PostDto read(@RequestParam Long no) {
		return mapper.read(no);
	}

	public void delete(Long no) {
		mapper.delete(no);
	}

	// 한 memberId의 좋아요 상태 반영
	public int handleLike(LikeRequest request) {
		String requestMemberId = request.getMemberId(); // 파라미터에서 memberId 추출
		Long requestPostNo = request.getPostNo();
		LikeDTO existing = mapper.getLike(requestMemberId, requestPostNo); // 매퍼에서 likeDto 객체 세팅
		if (existing == null) {
			mapper.addLike(request); // like테이블에 memberId 없으면 추가
			return 1;
		} else {
			int newLiked = existing.getLiked() == 0 ? 1 : 0; // 기존 liked 상태 반전 1-> 좋아요 0-> 해제
			mapper.handleLike(requestMemberId, newLiked, requestPostNo);
			return newLiked;
		}
	}

	public int countLikes(Long postNo) {// 총 좋아요 갯수 select count(*) 구문으로 liked가 1인 갯수
		return mapper.countLikes(postNo);
	}

	public ArrayList<MyPostResponse> getMyPost(String memberId) {
		return mapper.getMyPost(memberId);
	}

	public ArrayList<PostDto> getHomePosts() {
		return mapper.getHomePosts();
	}
}
