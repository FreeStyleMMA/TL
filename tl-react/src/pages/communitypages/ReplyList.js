import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import { ReplyCountContext } from '../../context/ReplyCountContext';

export default function ReplyList({ no, refreshReply }) { // no ->reply의 no
  const { no: postNo } = useParams();
  const [replyList, setReplyList] = useState([]);
  const { replyCount } = useContext(ReplyCountContext);

  useEffect(() => { // useEffct로 조건마다 이거
    const fetchReply = async (e) => {
      try {
        const response = await axios.get(`http://localhost:8080/reply/read?originNo=${postNo}`);
        setReplyList(response.data);
        console.log(ReplyList);
      } catch (error) {
        console.log(error);
      }
    }
    replyCount(postNo);//총 댓글 수 세기
    fetchReply();

  }, [no, postNo, refreshReply]); // 게시물 번호가 바뀌거나 refreshReply 토글 상태가 바뀔 때 댓글 불러오기.


  return (
    <div className="replyLayout" >
      {replyList.map(reply => (
        <div key={reply.postNo}>
          {reply.memberId}<br />
          {reply.date}<br />
          {reply.content}<br />
        </div>
      ))}

    </div>
  )
}