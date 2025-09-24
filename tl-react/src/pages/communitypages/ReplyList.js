import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { useParams } from "react-router-dom";
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { DeleteContext } from '../../context/DeleteContext';
import { useAuth } from '../auth/AuthContext';
import './reset.css';
import './ReplyList.css';


export default function ReplyList({ no, refreshReply }) { // no ->reply의 no
  const { no: postNo } = useParams();
  const [replyList, setReplyList] = useState([]);
  const { replyCount } = useContext(ReplyCountContext);
  const { handleReplyDelete } = useContext(DeleteContext);
  const { member } = useAuth();

  const fetchReply = async (e) => {
    try {
      const response = await axios.get(`http://localhost:8080/reply/read?originNo=${postNo}`);
      setReplyList(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => { // useEffct로 조건마다 이거
    replyCount(postNo);//총 댓글 수 세기
    fetchReply();
  }, [no, postNo, refreshReply]); // 게시물 번호가 바뀌거나 refreshReply 토글 상태가 바뀔 때 댓글 불러오기.


  const handleDelete = async (no, originNo) => {
    await handleReplyDelete(no, originNo);
    fetchReply();
  };

  return (
    <div id="replyLayout" >
      {replyList.map(reply => (
        <div className="reply_container" key={reply.no}>
          <div className="reply_left">
            <div className="reply_top">
              <div className="memberId">  {reply.memberId}<br /></div>
              <div className="date">{new Date(reply.createdAt).toLocaleString()}<br /></div>
            </div>
            <div className="reply_mid">
              <div className="text">{reply.content}<br /></div>
            </div>
          </div>
          <div className="reply_right">
            {/* {member.memberId === reply.memberId ? <button onClick={() => { handleReplyDelete(reply.no, reply.originNo) }}> 댓글 삭제</button> : null} */}
            {member.memberId === reply.memberId ?
              <button className="delete_box" onClick={() => { handleDelete(reply.no, reply.originNo) }}>
                <img src="/images/trashbin.png" alt="삭제" className="trashbin" />삭제</button> : null}
          </div>
        </div>
      ))}

    </div>
  )
}