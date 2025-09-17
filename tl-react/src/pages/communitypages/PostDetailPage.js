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

  const handleAddReply = async (newReply) => {
    try {
      const response = await axios.post("http://localhost:8080/reply/write", newReply);
      const savedReply = response.data; // 여기에는 DB에서 생성된 id 포함
      setReplys(prev => [...prev, savedReply]);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <div id="PostLayout">
        <div id='post_profile'>
          <img class='p_i' src="grey.jpg" /> {/* 이미지 넣는 태그 */}
        </div>
        <div class='post_title'>
          <p>글번호: {no}</p>
          <h2>제목:{title}</h2><br />
          {memberId}<br />
          {media && <img class="post_img" src={`http://localhost:8080${media}`} alt="media" />}
        </div>
        <div id="react">
          <div class="re">좋아요</div>
          <div class="re">댓글</div>
          <div class="re">공유</div>
        </div>
        {/* {content}<br /> */}
      </div>
      <div className="ReplyLayout">
        <ReplyWrite onAdd={addReply} /><br />
        <ReplyList replys={replys} /><br />

      </div>
    </>
  );

}