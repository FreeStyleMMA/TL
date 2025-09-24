import { Link } from "react-router-dom";
import React, { useState, useEffect, useRef, useContext } from 'react';
import axios from 'axios';
import './reset.css';
import './FreeBoard.css';
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";


export default function FreeBoard() {
  const { totalLikes } = useContext(LikeContext)
  const { totalReplies } = useContext(ReplyCountContext)
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [posts, setPosts] = useState([]);

  const fetchPosts = async (page) => {
    try {//현재 페이지 전송해서 게시물 리스트 받아오기
      const response = await axios.get(`http://localhost:8080/post/getFreeList?currentPage=${page}`);
      //데이터 ResponseDto = [postList:ArrayList<PostDto>,currentPage:int] 형태
      setPosts(response.data.postList);
      setTotalPages(response.data.totalPages);

    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);

  const handlePrev = () => {
    if (currentPage > 1) {
      fetchPosts(currentPage - 1);
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNext = () => {
    if (currentPage < totalPages) {
      fetchPosts(currentPage + 1);
      setCurrentPage(currentPage + 1);
    }
  };
  const handleCurrentPost = (page) => {
    setCurrentPage(page);
  };

  return (
    <div id="myLayout">
      <div id="posting">
        <Link id="posting_box" to='../posting'>글쓰기</Link>
      </div>
      <div id="freeBoardNav">
        <div >번호</div>
        <div >제목</div>
        <div >글쓴이</div>
        <div >날짜</div>
        <div>좋아요</div>
        <div>댓글</div>
      </div>
      <div id="freeBoardLayout">
        <ul>
          {posts.map((post) => (// 게시판 글목록
            <div >
              <div key={post.no} className="List">
                <Link className="Link" to={`./posts/${post.no}`}>
                  <div className="free_no">{post.no}</div>
                  <div className='free_title'>{post.title}</div></Link>
                <div className='free_memberId'>{post.memberId}</div>
                <div className='free_date'>{new Date(post.createdAt).toLocaleDateString()}</div>
                <div className="free_re">  {totalLikes[post.no] ?? 0}  </div>
                <div className="free_re">{totalReplies[post.no] ?? 0}</div>
              </div>
            </div>
          ))
          }
        </ul >

        <div style={{ marginTop: "20px" }}>
          <button onClick={handlePrev} disabled={currentPage === 1}>
            &lt; 이전
          </button>

          {Array.from({ length: totalPages }, (_, i) => (// 하단 페이지 블럭
            <button key={i} onClick={() => handleCurrentPost(i + 1)}
              style={{
                margin: "0 5px",
                background: currentPage === i + 1 ? "lightgrey" : "white",
              }}>
              {i + 1}
            </button>
          ))}
          <button onClick={handleNext} disabled={currentPage === totalPages}>
            다음 &gt;
          </button>
        </div>

      </div >
    </div >
  );
}