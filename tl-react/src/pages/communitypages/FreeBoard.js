import { Link } from "react-router-dom";
import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import './reset.css';
import './FreeBoard.css';
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";

export default function FreeBoard() {
  const { totalLikes, setInitialLikes } = useContext(LikeContext);
  const { totalReplies, setInitialReplies } = useContext(ReplyCountContext);

  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [posts, setPosts] = useState([]);

  const fetchPosts = async (page) => {
    try {
      const response = await axios.get(`http://localhost:8080/post/getFreeList?currentPage=${page}`);
      setPosts(response.data.postList);
      setTotalPages(response.data.totalPages || 1);
      setInitialLikes(response.data.postList);
      setInitialReplies(response.data.postList);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);

  // 한 페이지 이전/다음
  const handlePrev = () => { if (currentPage > 1) setCurrentPage(currentPage - 1); };
  const handleNext = () => { if (currentPage < totalPages) setCurrentPage(currentPage + 1); };
  const handleCurrentPost = (page) => setCurrentPage(page);

  // 🔹 페이지 블록 계산 (10개씩)
  const pagesPerBlock = 10;
  const currentBlock = Math.floor((currentPage - 1) / pagesPerBlock);
  const startPage = currentBlock * pagesPerBlock + 1;
  const endPage = Math.min(startPage + pagesPerBlock - 1, totalPages);

  // 🔹 블록 이전/다음
  const handlePrevBlock = () => {
    const prevBlockPage = startPage - 1;
    if (prevBlockPage >= 1) setCurrentPage(prevBlockPage);
  };

  const handleNextBlock = () => {
    const nextBlockPage = endPage + 1;
    if (nextBlockPage <= totalPages) setCurrentPage(nextBlockPage);
  };

  return (
    <div id="free_my_layout">
      <div id='posting_loc'>
        <Link id="posting" to='../posting'>글쓰기</Link>
      </div>

      <div id="free_nav">
        <div>번호</div>
        <div>제목</div>
        <div>글쓴이</div>
        <div>날짜</div>
        <div>좋아요</div>
      </div>

      <div id="free_layout">
        <ul>
          {posts.map((post) => (
            <li key={post.no}>
              <div className="free_List">
                <Link className="Link" to={`./posts/${post.no}`}>
                  <div className="free_no">{post.no}</div>
                  <div className='free_title'>{post.title}</div>
                </Link>
                <div className='free_memberId'>{post.memberId}</div>
                <div className='free_date'>{new Date(post.createdAt).toLocaleDateString()}</div>
                <div className="free_re">{totalLikes[post.no] ?? 0}</div>
              </div>
            </li>
          ))}
        </ul>

        <div id="page_block_container" style={{ marginTop: "20px" }}>
          <button onClick={handlePrevBlock} disabled={startPage === 1}>
            «
          </button>

          {Array.from({ length: endPage - startPage + 1 }, (_, i) => {
            const pageNum = startPage + i;
            return (
              <button
                key={pageNum}
                onClick={() => handleCurrentPost(pageNum)}
                style={{
                  margin: "0 5px",
                  background: currentPage === pageNum ? "lightgrey" : "white",
                }}
              >
                {pageNum}
              </button>
            );
          })}

          <button onClick={handleNextBlock} disabled={endPage === totalPages}>
            »
          </button>
        </div>
      </div>
    </div>
  );
}
