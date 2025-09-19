import { Link } from "react-router-dom";
import React, { useState, useEffect, useRef, useContext } from 'react';
import axios from 'axios';
import './reset.css';
import './ReviewBoard.css';
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";
import { useAuth } from "../auth/AuthContext";


export default function ReviewBoard() {
  const { totalReplies, replyCount } = useContext(ReplyCountContext);
  const { handleLike, totalLikes } = useContext(LikeContext);
  const { member } = useAuth();
  const [posts, setPosts] = useState([]);
  const [requestCursor, setRequestCursor] = useState(null); // 변경되면 fetch 트리거
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true); // 더 불러올게 있는지 확인
  const nextCursorRef = useRef(null); // 다음 동작 


  useEffect(() => {
    if (requestCursor === null) return;

    const controller = new AbortController(); // 취소요청 지원
    let mounted = true;

    const fetchPosts = async () => {
      setLoading(true);
      try {
        const url = requestCursor === 0
          ? `http://localhost:8080/post/getList?no=0`
          : `http://localhost:8080/post/getList?no=${requestCursor}`;
        const response = await axios.get(url, { signal: controller.signal });
        const newPosts = response.data || [];
        if (!mounted) return;

        if (newPosts.length === 0) {
          setHasMore(false);
        } else {
          setPosts(prev => [...prev, ...newPosts]);
          const minNo = Math.min(...newPosts.map(post => post.no));
          nextCursorRef.current = minNo; // ref에 저장 (상태 아님)
          setHasMore(true);

        }
      } catch (error) {
        console.error("fetchPosts error", error);
      } finally {
        if (mounted) setLoading(false);
      }
    };
    fetchPosts();
    return () => {
      mounted = false;
      controller.abort();
    };
  }, [requestCursor]);

  useEffect(() => {
    setRequestCursor(0);
  }, []);

  useEffect(() => {
    const handleScroll = () => {
      if (loading || !hasMore) return;

      if (window.scrollY + window.innerHeight >= document.body.scrollHeight - 100) {
        const next = nextCursorRef.current ?? 0; // 아직 계산 안됐으면 0으로(또는 원하는 기본)
        if (next !== requestCursor) {
          setRequestCursor(next);
        }
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [loading, hasMore, requestCursor]);

  useEffect(() => {
    posts.forEach(post => replyCount(post.no));
  }, [posts]);

  return (
    <div id="myLayout">
      <div id='postLayout'>
        <div>
          <Link id="posting_box" to='../posting'>글쓰기</Link>
        </div>

        <div id="left">
          <div id="posts">
            {posts.map(post => (

              <div key={post.no} className="post">  <br />
                <div className="post_profile">
                  <img className="p_i" src="/images/grey.jpg" />
                  <div className="p_n">r/entertai등 커뮤이름이나 #언급? ____n시간 전</div>
                  {/* <div id="p_b">커뮤니티 가입</div> */}
                </div>
                <Link
                  to={`./posts/${post.no}`}
                >
                  <div className="post_title">
                    <h3>{post.title}</h3>
                  </div>
                  {/* <p>{post.content}</p> */}
                  <br />
                  {post.media && <img className="post_img" src={`http://localhost:8080${post.media}`} alt="media" />}
                </Link>

                <div className="react">
                  <button onClick={() => handleLike(member.memberId, post.no)} className="re">
                    좋아요 {totalLikes[post.no] ?? 0}
                  </button>
                  <div className="re">댓글 {totalReplies[post.no] ?? 0}</div>
                  <div className="re">공유</div>
                </div>
              </div>
            ))}
          </div>
        </div>

      </div>
    </div >
  )
}