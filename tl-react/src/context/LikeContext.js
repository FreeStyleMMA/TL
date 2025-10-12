import React, { createContext, useState, useEffect } from "react";
import axios from 'axios';
import { useAuth } from "../pages/auth/AuthContext";


export const LikeContext = createContext();

export function LikeProvider({ children, postNo, memberId }) {
  const [liked, setLiked] = useState({});
  const [totalLikes, setTotalLikes] = useState({});
  const { member } = useAuth();


  const handleLike = async (memberId, postNo) => {
    try {
      const response = await axios.post(`http://localhost:8080/post/handleLike`,
        { memberId: memberId, postNo: postNo }
      );
      setLiked(prev => ({ ...prev, [postNo]: response.data.liked }));
      console.log("liked: ", response.data.newLiked)
      setTotalLikes(prev => ({ ...prev, [postNo]: response.data.totalLikes }));
      console.log("totalLikes: ", response.data.totalLikes)
    }
    catch (error) {
      console.log(error)
    }
  }

  // useEffect(() => {
  //   const fetchLikes = async () => {
  //     try {
  //       const response = await axios.get(
  //         `http://localhost:8080/post/countLikes?postNo=${postNo}`
  //       );
  //       setLiked(response.data.newLiked);
  //       setTotalLikes(response.data.totalLikes);
  //     } catch (error) {
  //       console.log(error);
  //     }
  //   };

  //   fetchLikes();
  // }, []);


  const setInitialLikes = async (newPosts) => { // 마운팅 시 초기 like 값 세팅
    const initialLikes = {};
    const initialLiked = {};
    const postNos = newPosts.map(post => post.no);

    try {
      const response = await axios.get(
        `http://localhost:8080/post/initialLikes?postNos=${postNos.join(",")}&memberId=${member.memberId}`
      );
      response.data.forEach(item => {
        initialLikes[item.postNo] = item.count; // 총 liked=1 갯수 카운팅.
        initialLiked[item.postNo] = item.liked; // 기존 liked 상태 세팅
      });
    } catch (err) {
      console.error(err);
      newPosts.forEach(p => {
        if (!(p.no in initialLikes)) initialLikes[p.no] = 0;
        if (!(p.no in initialLiked)) initialLiked[p.no] = 0;
      });
    }
    setTotalLikes(prev => ({ ...prev, ...initialLikes }));
    setLiked(prev => ({ ...prev, ...initialLiked }));
  };

  return (
    <LikeContext.Provider value={{ handleLike, liked, totalLikes, setInitialLikes }}>
      {children}
    </LikeContext.Provider>
  )
}

