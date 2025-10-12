import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../auth/AuthContext';
import './SignIn.css'

export default function Login({ onSignIn }) {
  const location = useLocation();
  const { loginComplete } = useAuth();
  const [memberId, setMemberId] = useState("");
  const [memberPw, setMemberPw] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {//서버에 로그인 요청
      const response = await axios.post("http://localhost:8080/api/signIn", {
        memberId,
        memberPw
      }, { withCredentials: true });
      console.log("로그인 응답 데이터:", response.data);
      //로그인 성공 시 로직c
      const accessToken = response.data.accessToken
      if (accessToken) {
        loginComplete(accessToken)//세션이 로그인 정보 저장
        onSignIn && onSignIn(accessToken); // 로그인 이전 페이지로 이동
      }
    }
    catch (error) {
      console.error(error);
      console.log("login message: 서버 오류 발생")
    }
  }
  return (
    <div>
      <form id="myLayout" onSubmit={handleLogin}>
        <div id="loginLayout">
          <div>
            <input id="id_box"
              type="text"
              placeholder="아이디"
              value={memberId} onChange={(e) => setMemberId(e.target.value)} />
          </div>
          <div>
            <input id="pw_box"
              type="password"
              placeholder="비밀번호"
              value={memberPw} onChange={(e) => setMemberPw(e.target.value)} />
          </div>
          <button type="submit" id="login_button" >로그인</button>
        </div>
      </form>
    </div>
  )
}