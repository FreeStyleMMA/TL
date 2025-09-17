import { Link } from "react-router-dom";
import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

export default function ReviewBoard() {
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
        // 같은 값으로 set하면 effect가 재실행되지 않으므로 안전
        if (next !== requestCursor) {
          setRequestCursor(next);
        }
      }
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [loading, hasMore, requestCursor]);



  return (
    <div>
      후기 게시판
      <div>
        <Link to='../posting'>글쓰기</Link>
      </div>
      <div className="posts">
        {posts.map(post => (
          <div key={post.no} className="post">  <br />
            <Link
              to={`./posts/${post.no}`}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <h3>{post.title}</h3>
              <p>{post.content}</p>
              <br />
              {post.media && <img src={`http://localhost:8080${post.media}`} alt="media" style={{ height: 500, width: 400 }} />}
            </Link>

          </div>


        ))}
      </div>

    </div>
  )
}