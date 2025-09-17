import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../auth/AuthContext';

export default function Login({ onSignIn }) {
  const location = useLocation();
  const { loginComplete } = useAuth();
  const [memberId, setMemberId] = useState("");
  const [memberPw, setMemberPw] = useState("");
  const nav = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {//서버에 로그인 요청
      const response = await axios.post("http://localhost:8080/api/signIn", {
        memberId,
        memberPw
      }, { withCredentials: true });
      console.log("login message: " + response.data.message);

      //로그인 성공 시 로직
      if (response.data.loginSuccess) {
        const role = response.data.role
        console.log("authorities: ", role);
        console.log("멤버아이디:", memberId)

        loginComplete({ memberId: memberId, role: role })//세션이 로그인 정보 저장
        onSignIn && onSignIn({ memberId, role }); // 로그인 이전 페이지로 이동

        //로그인 성공 후 role에 따른 페이지 분기는 일단 모두 홈페이지로.
        // if (role.includes("ADMIN")) {
        //   nav('/');        // 관리자 페이지
        // } else if (role.includes("ROLE_MEMBER")) {
        //   nav('/');       // 일반 회원 페이지
        // } else {
        //   nav('/');       // 그 외 기본 페이지
        // }

      }
    }
    catch (error) {
      console.error(error);
      console.log("login message: 서버 오류 발생")
    }
  }
  return (
    <div>
      <form className="pageLayout" onSubmit={handleLogin}>
        <div>
          ID:
          <input className="loginBox" type="text" placeholder="id를 입력하세요" value={memberId} onChange={(e) => setMemberId(e.target.value)} />
        </div>
        <div>
          pw:
          <input className="loginBox" type="password" placeholder="패스워드를 입력하세요" value={memberPw} onChange={(e) => setMemberPw(e.target.value)} />
        </div>
        <button type="submit" className="button" >로그인</button>
      </form>
    </div>
  )
}