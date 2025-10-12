import { useParams } from "react-router-dom";
import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import ReplyList from "./ReplyList";
import ReplyWrite from "./ReplyWrite";
import { useAuth } from "../auth/AuthContext";
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";
// import Delete from '../../components/DeletePage';
import { DeleteContext } from '../../context/DeleteContext';
import './PostDetailPage.css'

export default function PostDetailPage() {
  const { no: postNo } = useParams(); // ReviewBoard에서 Param으로 no(글번호) 받기. 연결은 App.js 참조
  const { totalReplies, ReplyCount } = useContext(ReplyCountContext);
  const { handleLike, totalLikes, liked, setInitialLikes } = useContext(LikeContext);
  const { handlePostDelete } = useContext(DeleteContext)
  const [title, setTitle] = useState(null);
  const [content, setContent] = useState("");
  const [memberId, setMemberId] = useState("");
  const [media, setMedia] = useState("");
  const [refreshReply, setRefreshReply, setInitialReplies] = useState(false);
  const [date, setDate] = useState("")
  const [replies, setReplies] = useState({});  // { [postNo]: count }
  const { member } = useAuth();

  useEffect(() => {
    const handlePostData = async (e) => {
      try {
        //data 받아오기 PostDto 형식으로. 
        const response = await axios.get(`http://localhost:8080/post/read?no=${postNo}`)
        setTitle(response.data.title);
        setContent(response.data.content);
        setMemberId(response.data.memberId);
        setMedia(response.data.media);
        setDate(new Date(response.data.createdAt).toLocaleDateString());
        setInitialLikes([response.data]);
        setInitialReplies([response.data]);
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
  useEffect(() => {
    handleLike();
  }, []);
  return (
    <div id="pageLayout">

      <div id="postLayout">

        <div id='post_head'>
          {/* <p>글번호: {postNo}</p> */}
          <div id="ph_1">
            <div id="post_writer">  {memberId}</div>
            <div id="post_date"> {date}<br /></div>
          </div>
        </div>
        <div id="ph_2">
          <div id="post_title">{title}<br /></div>
        </div>
        <div id="post_body">
          {media && <img className="post_img"
            src={`http://localhost:8080${media}`}
            alt="media" />}<br /><br />
          <div id="post_content"> {content}</div>
        </div>

        <div id="pdp_react">
          <button onClick={() => { handleLike(member.memberId, postNo) }}
            className="pdp_re">
            <img src={liked[postNo] === 1 ? "/images/like.png" : "/images/like_grey.png"}
              className="pdp_re_img" />
            {totalLikes[postNo] ?? 0}
          </button>
          <div className="pdp_re">
            <img src="/images/reply.png"
              alt="삭제"
              className="pdp_re_img" />
            {totalReplies[postNo] ?? 0}
          </div>
          {member.memberId === memberId ?
            < button onClick={() => { handlePostDelete(postNo) }}
              className="pdp_re">
              <img src="/images/trashbin.png"
                alt="삭제"
                className="pdp_re_img" />

            </button> : null}
        </div>
        {/* {content}<br /> */}
      </div >

      <div className="ReplyLayout">
        <ReplyWrite onReplyAdded={handleAddReply} /><br />
        <ReplyList refreshReply={refreshReply} /><br />
      </div>
    </div>
  );

}