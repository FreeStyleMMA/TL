import './Homepage.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from "./auth/AuthContext";
import './reset.css'

export default function Homepage() {
  const { member } = useAuth();
  const [posts, setPosts] = useState([]);
  const [performances, setPerformances] = useState([]);
  const [loading, setLoading] = useState(false);

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
        탑 이미지 영역
      </div>
      <div id="home_mid">
        <div id="home_mid1">
          <div className="mid_title">
            신규 오픈
          </div>
          <div id="perform_list">
            {performances.map(perform =>
              <div className="perform_box" key={perform.favId}>
                <div className="performe_poster">
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
      </div>
      <div id="bottom">

      </div>
    </div>
  )
}
