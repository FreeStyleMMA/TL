import './Homepage.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from "./auth/AuthContext";
import './reset.css'
import { useNavigate, Link } from "react-router-dom";

export default function Homepage() {
  const { member } = useAuth();
  const [posts, setPosts] = useState([]);
  const [performances, setPerformances] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigatie = useNavigate();
  useEffect(() => {
    const fetchPosts = async () => {
      setLoading(true)
      try {
        const response = await axios.get(`http://localhost:8080/post/getHomePosts`);
        setPosts(response.data);
      } catch (error) {
        console.log(error);
      }
    }
    const fetchPerformances = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/tl/getHomePerform`)
        setPerformances(response.data); //[{content:oo , originTitle:00, createdAt:yyyy.mm.dd.time}] 의 ArrayList 형태. map으로
      } catch (error) {
      }
    }
    fetchPosts();
    fetchPerformances();
  }, [])
  return (
    <div id="home_layout">
      <div id="home_top">
        <div id="home_top_image_container">
          <img id="home_top_image" src="/images/home3.jpg" />
        </div>
      </div>
      <div id="home_mid">
        <div id="home_mid1">
          <div className="mid_title">
            신규오픈
          </div>
          <div id="perform_list">
            {performances
              .filter((perform) => perform.perPoster)
              .map(perform =>
                <div className="perform_box" key={perform.favId}>
                  <div className="perform_poster">
                    <img
                      className="perform_poster_img"
                      alt={perform.perTitle}
                      src={perform.perPoster} //공연 포스터
                    /></div>
                  <div className="perform_contents">
                    <div className="perform_title">&nbsp;{perform.perTitle}</div>
                    <div className="perform_run_date">
                      &nbsp;&nbsp;{perform.perStartD}
                      {/* &nbsp;~
                    <br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{perform.perEndD} */}
                    </div>
                  </div>
                </div>
              )}
          </div>
        </div>

        <div id="home_mid2">
          <div className="mid_title">
            핫 게시물
          </div>
          <div id="home_post_list">
            {posts.map(post => (
              <div id="home_post_box" key={post.no}>
                <div id="home_post_media">
                  <img className="home_poster_img"
                    src={`http://localhost:8080${post.media}`}
                  />
                </div>
                <div className="home_post_text">
                  <div className="home_post_title">
                    {post.title}
                  </div>
                  {/* <div className="home_post_content">
                    {post.content}
                  </div> */}
                </div>
              </div>
            ))}
          </div>
        </div>
        <div id="home_mid3">
          <div id="r1">
            <p className="r_text">뮤지컬&lt;보더라인&gt;</p>
            <p className="sub_text3">2025.07.15~2025.10.12</p>
          </div>
          <img className="r_img" alt="images/grey.jpg" src="/images/borderline.jpg" />
          <div id="r2">
            <div className="r_text2">뮤지컬&lt;보더라인&gt;</div>
            <div className="r_text2">뮤지컬 &lt;위대한개츠비&gt;(The Great Gatsby)</div>
            <div className="r_text2">뮤지컬&lt;레드북&gt;</div>
            <div className="r_text2">뮤지컬&lt;브로드웨이 42번가&gt;</div>
            <div className="r_text2">뮤지컬&lt;트레드밀&gt;</div>
          </div>
        </div>
      </div>
      <div id="bottom">
        <div id="bottom_left">

        </div>
        <div id="bottom_right">
          Theater-Link
        </div>
      </div>
    </div>
  )
}
