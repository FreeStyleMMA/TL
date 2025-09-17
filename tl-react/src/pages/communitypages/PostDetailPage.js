import { useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReplyList from "./ReplyList";
import ReplyWrite from "./ReplyWrite";


export default function PostDetailPage() {
  const { no } = useParams(); // ReviewBoard에서 Param으로 no(글번호) 받기. 연결은 App.js 참조

  const [title, setTitle] = useState(null);
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const [media, setMedia] = useState("");
  const [replys, setReplys] = useState([]);//기존 댓글목록 리스트

  useEffect(() => {
    const handlePostData = async (e) => {
      try {
        //data 받아오기 PostDto 형식으로. 
        const response = await axios.get(`http://localhost:8080/post/read?no=${no}`)
        setTitle(response.data.title);
        setContent(response.data.content);
        setMemberId(response.data.memberId);
        setMedia(response.data.media);
      } catch (error) {
        console.log("서버에러", error);
      }
    }
    handlePostData();
  }, [])

  const addReply = (newReply) => {
    setReplys(prev => [...prev, newReply]);
  };

  return (
    <>
      <div className="PostLayout">
        <p>글번호: {no}</p>
        <h2>제목:{title}</h2><br />
        {memberId}<br />
        {content}<br />
        {media && <img src={`http://localhost:8080${media}`} alt="media" style={{ height: 500, width: 400 }} />}
      </div>
      <div className="ReplyLayout">
        <ReplyWrite onAdd={addReply} /><br />
        <ReplyList replys={replys} /><br />

      </div>
    </>
  );

}