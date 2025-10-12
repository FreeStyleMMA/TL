package com.tl.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.tl.dto.FavoritePostDTO;
import com.tl.dto.MemberVO;
import com.tl.dto.MyInfoResponse;
import com.tl.dto.SignUpRequest;

public interface MemberMapper {
	public void signUp(SignUpRequest request);
    public MemberVO signIn(@Param("memberId") String memberId, @Param("memberPw")String memberPw);
	//로그인 및 jwt 토큰 인증에 사용
	public MemberVO findByMemberId(String memberId);
	public FavoritePostDTO getFavorite(@Param("memberId")String memberId,@Param("liked")int liked);
	public void handleFavorite(@Param("memberId")String memberId, @Param("memberId")Long per_id);
	public MyInfoResponse getMyInfo(String memberId);
	public void saveOrUpdate(
			@Param("memberId") String memberId,
			@Param("refreshToken") String refreshToken,
			@Param("expiration") Date expiration
			);
	public String checkRefreshToken(String refreshToken);
}
