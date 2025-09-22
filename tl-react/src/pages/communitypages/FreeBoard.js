import { Link } from "react-router-dom";
import React, { useState, useEffect, useRef, useContext } from 'react';
import axios from 'axios';
import './reset.css';
// import './FreeBoard.css';
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";
import { useAuth } from "../auth/AuthContext";


export default function FreeBoard() {
  const { totalReplies, replyCount } = useContext(ReplyCountContext);
  const { handleLike, totalLikes } = useContext(LikeContext);
  const { member } = useAuth();
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);



  useEffect(() => {

    const controller = new AbortController(); // 취소요청 지원
    let mounted = true;

    const fetchPosts = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`http://localhost:8080/post/getFreeList`);
        const newPosts = response.data || [];

        setPosts(prev => [...prev, ...newPosts]);
        const minNo = Math.min(...newPosts.map(post => post.no));
      }
      catch (error) {
        console.error("fetchPosts error", error);
      }
    };
    fetchPosts();
    return () => {
      mounted = false;
      controller.abort();
    };
  });




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