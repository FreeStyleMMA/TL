import './ComHomePage.css';
import './reset.css';
import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import { ReplyCountContext } from '../../context/ReplyCountContext';
import { LikeContext } from "../../context/LikeContext";
import { useAuth } from "../auth/AuthContext";


export default function ComeHomePage() {
  const { totalReplies, setInitialReplies } = useContext(ReplyCountContext);
  const { handleLike, totalLikes, setInitialLikes } = useContext(LikeContext);
  const { member } = useAuth();

  const [topPosts, setTopPosts] = useState([]);
  const [middlePosts, setMiddlePosts] = useState([]);

  const fetchTop = async () => {
    try {
      const response = await axios.get("http://localhost:8080/post/getComHomeTop")
      const newPosts = Array.isArray(response.data) ? response.data : [];
      setTopPosts(newPosts);
    } catch (error) {
      console.log("comhomepage fetchTop", error);
    }
  }

  const fetchMiddle = async () => {
    try {
      const response = await axios.get("http://localhost:8080/post/getReviewList?no=0")
      const newPosts = Array.isArray(response.data) ? response.data : [];
      setMiddlePosts(newPosts);
      setInitialReplies(newPosts);
      setInitialLikes(newPosts);
    } catch (error) {
      console.log("comhomepage fetchMiddle", error);

    }
  }

  useEffect(() => {
    fetchTop();
    fetchMiddle();
  }, []);

  return (
    <div id="myLayout">
      <div id="story">

        <div id="story_container">
          {topPosts.map(post =>
            <Link to={`./posts/${post.no}`} style={{ position: 'relative' }} key={post.no}>
              <div className="story_box">
                {/* 배경 전용 */}
                <div
                  style={{
                    position: 'absolute',
                    top: 0, left: 0, right: 0, bottom: 0,
                    backgroundImage: post.media ? `url(http://localhost:8080${post.media})` : 'none',
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    opacity: 0.9,
                    borderRadius: '25px',
                    zIndex: 0
                  }}
                />
                <div className='story_box_link'
                  style={{ position: 'relative', zIndex: 1, padding: 10 }}>
                  <div className='story_box_title'>{post.title}</div>
                  <div className='story_box_content'>{post.content}</div>
                  {/* <div className='story_box_text'>{new Date(post.date).toLocaleDateString()}</div> */}
                </div>
              </div>
            </Link>
          )}
        </div>
      </div>
      <div id='mid'>
        <div id='mid_left'>

        </div>
        <div id='mid_right'>
          <div id='postLayout'>
            <div id="comhome__post_box">
              {middlePosts.map(post => (
                <div key={post.no} className="comhome_post">  <br />
                  <div className="comhome_post_profile">
                    <img className="comhome_profile_image" src="/images/grey.jpg" />
                    <div className="comhome_post_date">
                      {new Date(post.createdAt).toLocaleDateString()}</div>
                    {/* <div id="p_b">커뮤니티 가입</div> */}
                  </div>
                  <Link className="comhome_post_title"
                    to={`./posts/${post.no}`}
                  >
                    <div >
                      <h3>{post.title}</h3>
                    </div>
                    {/* <p>{post.content}</p> */}
                    <br />
                    {post.media &&
                      <img className="comhome_post_img"
                        src={`http://localhost:8080${post.media}`}
                        alt="media" />}
                  </Link>

                  <div className="comhome_react">
                    <button onClick={() => handleLike(member.memberId, post.no)}
                      className="comhome_re">
                      <img src="/images/like.png"
                        alt="댓글"
                        className="comhome_re_img" />
                      &nbsp;&nbsp;{totalLikes[post.no] ?? 0}
                    </button>
                    <div className="comhome_re">
                      <img src="/images/reply.png"
                        alt="댓글"
                        className="comhome_re_img" />
                      &nbsp;&nbsp;{totalReplies[post.no] ?? 0}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

    </div>
  );
}