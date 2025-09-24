import React, { createContext, useState, useEffect } from "react";
import axios from 'axios';

export const LikeContext = createContext();

export function LikeProvider({ children, postNo, memberId }) {
  const [liked, setLiked] = useState({});
  const [totalLikes, setTotalLikes] = useState({});


  const handleLike = async (memberId, postNo) => {
    try {
      const response = await axios.post(`http://localhost:8080/post/handleLike`,
        { memberId: memberId, postNo: postNo }
      );
      setLiked(prev => ({ ...prev, [postNo]: response.data.newLiked }));
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

  const setInitialLikes = async (newPosts) => {
    const initialLikes = {};
    const array = Array.isArray(newPosts) ? newPosts : [];
    const postNos = array.map(post => post.no);
    try {
      const response = await axios.get(`http://localhost:8080/post/initialLikes?postNos=${postNos.join(",")}`
      );
      // response.data = [{ postNo: 1, totalLikes: 5 }, ...] 형태로 받아오기
      response.data.forEach(item => {
        initialLikes[item.postNo] = item.count;
      });
    } catch (err) {
      console.error(err);
      newPosts.forEach(p => { if (!(p.no in initialLikes)) initialLikes[p.no] = 0; }); // 없으면 기본값 0 세팅.
    }
    setTotalLikes(prev => ({ ...prev, ...initialLikes }));

  };

  return (
    <LikeContext.Provider value={{ handleLike, liked, totalLikes, setInitialLikes }}>
      {children}
    </LikeContext.Provider>
  )
}

