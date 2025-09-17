import axios from 'axios';
import React, { useState, useEffect } from 'react';
import './PostWrite.css';
import { useAuth } from "../auth/AuthContext";
import { useParams } from "react-router-dom";

export default function ReplyWrite({ onAdd }) {
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const { no } = useParams();


  const { member } = useAuth(); // 세션에서 member 정보 가져오기
  console.log("member:", member);

  useEffect(() => {
    if (member) {
      setMemberId(member.memberId);
    }
  }, [member]);

  const handleReply = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/reply/write', {
        memberId: memberId,
        content: content,
        originNo: no
      }).then(response => {
        console.log("업로드 성공");
      })
    } catch (error) {
      console.error("업로드 실패", error);
    }
    onAdd({ id: memberId, content: content });
  }

  return (
    <div>
      <form className='postLayout' onSubmit={handleReply} >
        <h3>댓글작성</h3><br />
        작성자 id:{memberId}
        <textarea className='textBox' value={content} placeholder='내용을 입력하세요' onChange={(e) => setContent(e.target.value)}
          style={{ width: 700, height: 50 }} />
        <button className='submitButton' type="submit">댓글달기</button>
      </form>
    </div >
  )
}