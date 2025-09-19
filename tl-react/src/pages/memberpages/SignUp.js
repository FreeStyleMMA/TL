import axios from 'axios'
import React, { useState, useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom';




export default function SignUp() {
  const nav = useNavigate();
  const [memberId, setMemberId] = useState("");
  const [memberPw, setMemberPw] = useState("");
  const [memberName, setMemberName] = useState("");

  const handleSignUp = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/signUp",
        { memberId, memberPw, memberName }
        , { withCredentials: true }
      );
      console.log(response.data.message);
      if (response.data.SignUpSuccess) {
        alert("회원 가입 성공");
        nav("/signin");
      }
    } catch (error) {
      console.log("서버 에러 발생")
    }
  }


  return (
    <>
      <form onSubmit={handleSignUp}>
        <input
          className="inputBox"
          value={memberId}
          onChange={(e) => setMemberId(e.target.value)}
          placeholder="아이디"
        />
        <input
          className="inputBox"
          type="password"
          value={memberPw}
          onChange={(e) => setMemberPw(e.target.value)}
          placeholder="비밀번호"
        />
        <input
          className="inputBox"
          value={memberName}
          onChange={(e) => setMemberName(e.target.value)}
          placeholder="이름"
        />
        <button type="submit">회원가입</button>
      </form>
    </>
  )
}