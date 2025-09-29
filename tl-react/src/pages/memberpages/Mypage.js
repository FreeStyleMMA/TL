import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useAuth } from '../auth/AuthContext';
import './Mypage.css';
import '../reset.css';


export default function Mypage() {
  const { member } = useAuth();
  // const [per_id, setPer_id] = useState(0);
  // const [myName, setMyName] = useState("");
  // const [joinDate, setJoinDate] = useState("");
  // const [myNicl, setMyNick] = useState("");
  const [myInfo, setMyInfo] = useState({});
  const [myPosts, setMyPosts] = useState([]);
  const [myReplies, setMyReplies] = useState([]);


  const getMyInfo = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/getMyInfo?memberId=${member.memberId}`)
      setMyInfo(response.data) //[{memberName:oo, memberId:oo, createdAt:yyyy.mm.dd. time}] 형태
      console.log(response.data)
    } catch (error) {
      console.log(error);
    }
  }

  const getMyPosts = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/post/getMyPost?memberId=${member.memberId}`)
      setMyPosts(response.data); //[{title:oo , no:0, createdAt:yyyy.mm.dd.time}] 의 ArrayList 형태. map으로
    } catch (error) {

    }
  }

  const getMyReplies = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/reply/getMyReplies?memberId=${member.memberId}`)
      setMyReplies(response.data); //[{content:oo , originTitle:00, createdAt:yyyy.mm.dd.time}] 의 ArrayList 형태. map으로
    } catch (error) {

    }
  }
  useEffect(() => {
    getMyInfo();
    getMyPosts();
    getMyReplies();
  }, []);

  return (
    <div id="myLayout">
      <div id="mypageLayout">

        <div id="m_title">
          <div id="title_layout"> 마이페이지</div>
        </div>
        <div id="m_info">
          <div id="info_left">
            <div id="info_title"> 내정보</div>
            <div id="info_image">  이미지 </div>
          </div>
          <div id="info_right">
            <div id="info_name">회원 이름 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {myInfo.memberName}</div>
            <div id="info_date">가입일 &nbsp;{new Date(myInfo.createdAt).toLocaleDateString}</div>
          </div>
        </div>
        <div id="post_container">
          <div id="post_name">내가 쓴 글</div>

          {myPosts.map(post =>
            <div className="m_post" key={post.no}>
              <div className="m_post_title">·제목: &nbsp;{post.title}</div>
              {/* <div className="m_post_no">글번호&nbsp;&nbsp;{post.no}</div> */}
              <div className="m_post_date">작성일&nbsp;&nbsp;{new Date(post.createdAt).toLocaleDateString}</div>
            </div>
          )}
        </div>
        <div id="reply_container">
          {myReplies.map(reply =>
            <div className="m_reply" key={reply.originTitle}>
              <div className="reply_content">내용{reply.content}</div>
              <div className="replyt_no">원글제목{reply.originTitle}</div>
              <div className="reply_date">작성일{new Date(reply.createdAt).toLocaleDateString}</div>
            </div>
          )}
        </div>
        
        <div id="m_favorite">
          <div className="favorite_title">공연이름</div>
          <div className="favorite_poster">공연포스터</div>
          <div className="favorite_run_date">상영일자</div>
        </div>
      </div>
    </div>
  )
}