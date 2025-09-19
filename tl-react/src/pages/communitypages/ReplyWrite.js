import axios from 'axios';
import React, { useState, useEffect } from 'react';
import './PostWrite.css';
import { useAuth } from "../auth/AuthContext";
import { useParams } from "react-router-dom";

export default function ReplyWrite({ onReplyAdded }) { // onReplyAdded로 PostDetailPage에 댓글 추가 알림 세팅
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const { no: postNo } = useParams(); // 파라미터 no는 postNo >> reply 처리하는 no와 겹침 방지.


  const { member } = useAuth(); // 세션에서 member 정보 가져오기
  console.log("member:", member);

  useEffect(() => {
    if (member) {
      setMemberId(member.memberId);
    }
  }, [member]);

  const handleReply = async (e) => { // 댓글 등록 처리 파트
    try {
      const response = await axios.post('http://localhost:8080/reply/write', {
        memberId: memberId,
        content: content,
        originNo: postNo
      }).then(response => {
        console.log("업로드 성공");
      })
    } catch (error) {
      console.error("업로드 실패", error);
    }

    onReplyAdded(); //PostDetailPage에 댓글 추가 알림 전송
  }

  return (
    <div>
      <form className='postLayout'  >
        <h3>댓글작성</h3><br />
        작성자 id:{memberId}
        <textarea className='textBox' value={content} placeholder='내용을 입력하세요' onChange={(e) => setContent(e.target.value)}
          style={{ width: 700, height: 50 }} />
        <button value={onReplyAdded} className='submitButton' onClick={handleReply}>댓글달기</button>
      </form>
    </div >
  )
}