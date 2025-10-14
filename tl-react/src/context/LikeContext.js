import React, { createContext, useState } from "react";
import axios from 'axios';
import { useAuth } from "../pages/auth/AuthContext";

export const LikeContext = createContext();

export function LikeProvider({ children }) {
  const [liked, setLiked] = useState({});
  const [totalLikes, setTotalLikes] = useState({});
  const { member } = useAuth();

  // 좋아요 클릭 처리
  const handleLike = async (memberId, postNo) => {
    try {
      const response = await axios.post(`http://localhost:8080/post/handleLike`, {
        memberId,
        postNo
      });
      setLiked(prev => ({ ...prev, [postNo]: response.data.liked }));
      setTotalLikes(prev => ({ ...prev, [postNo]: response.data.totalLikes }));
    } catch (error) {
      console.error("Like error:", error);
    }
  }

  // 초기 좋아요값 세팅
  const setInitialLikes = async (newPosts) => {
    if (!member) return; // member 없으면 실행 안함
    const initialLikes = {};
    const initialLiked = {};
    const postNos = newPosts.map(post => post.no);

    try {
      const response = await axios.get(
        `http://localhost:8080/post/initialLikes?postNos=${postNos.join(",")}&memberId=${member.memberId}`
      );
      response.data.forEach(item => {
        initialLikes[item.postNo] = item.count;
        initialLiked[item.postNo] = item.liked;
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
  );
}
