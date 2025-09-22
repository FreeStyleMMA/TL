import { useParams } from "react-router-dom";
import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import ReplyList from "./ReplyList";
import ReplyWrite from "./ReplyWrite";
import { useAuth } from "../auth/AuthContext";
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";

export default function PostDetailPage() {
  const { no: postNo } = useParams(); // ReviewBoard에서 Param으로 no(글번호) 받기. 연결은 App.js 참조
  const { totalReplies, ReplyCount } = useContext(ReplyCountContext);
  const { handleLike, totalLikes } = useContext(LikeContext);

  const [title, setTitle] = useState(null);
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const [media, setMedia] = useState("");
  const [refreshReply, setRefreshReply] = useState([]);
  const [date, setDate] = useState("")

  const [replies, setReplies] = useState({});  // { [postNo]: count }
  useEffect(() => {
    const handlePostData = async (e) => {
      try {
        //data 받아오기 PostDto 형식으로. 
        const response = await axios.get(`http://localhost:8080/post/read?no=${postNo}`)
        setTitle(response.data.title);
        setContent(response.data.content);
        setMemberId(response.data.memberId);
        setMedia(response.data.media);
        setDate(new Date(response.data.date).toLocaleDateString());
        console.log(new Date(response.data.date).toLocaleDateString());
      } catch (error) {
        console.log("서버에러", error);
      }
    }
    handlePostData();
  }, []);



  const handleAddReply = () => {
    setRefreshReply(prev => !prev); // 상태 반전으로 useEffect 활성화
    setReplies(prev => prev + 1); // 댓글 수 로컬 증가
  };


  return (
    <>
      <div id="PostLayout">
        <div id='post_profile'>
          <img class='p_i' src="grey.jpg" /> {/* 이미지 넣는 태그 */}
        </div>
        <div class='post_title'>
          <p>글번호: {postNo}</p>
          <h2>제목:{title}</h2><br />
          작성일:{date}<br />
          작성자: {memberId}<hr />
          내용<br />
          {content}<br /><br />
          {media && <img class="post_img" src={`http://localhost:8080${media}`} alt="media" />}<br /><br />
        </div>
        <div id="react">
          <button onClick={() => { handleLike(memberId, postNo) }} className="re">좋아요 {totalLikes[postNo] ?? 0}</button>
          <div className="re">댓글 {totalReplies[postNo] ?? 0}</div>
          <div className="re">공유</div>
          <div class="re">공유</div>
        </div>
        {/* {content}<br /> */}
      </div >
      <div className="ReplyLayout">
        <ReplyWrite onReplyAdded={handleAddReply} /><br />
        <ReplyList refreshReply={refreshReply} /><br />
      </div>
    </>
  );

}