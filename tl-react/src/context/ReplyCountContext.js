import React, { createContext, useState } from "react";
import axios from 'axios'

export const ReplyCountContext = createContext();

export function ReplyCountProvider({ children }) {

  const [totalReplies, setTotalReplies] = useState({});

  const replyCount = async (postNo) => {
    try {
      const response = await axios.get(`http://localhost:8080/reply/count?originNo=${postNo}`);
      setTotalReplies(prev => ({ ...prev, [postNo]: response.data }));
    }
    catch (error) {
      console.log(error);
    }
  };

  const setInitialReplies = async (newPosts) => {
    const initialReplies = {};
    const array = Array.isArray(newPosts) ? newPosts : [];
    const postNos = array.map(post => post.no);
    try {
      const response = await axios.get(`http://localhost:8080/reply/initialReplies?postNos=${postNos.join(",")}`)
      // response.data = [{ postNo: 1, totalLikes: 5 }, ...] 형태로 받아오기
      console.log("initialCount 실행 되나", response.data)
      response.data.forEach(item => {
        initialReplies[item.postNo] = item.count;
      });
    } catch (err) {
      console.error(err);
      newPosts.forEach(p => { if (!(p.no in initialReplies)) initialReplies[p.no] = 0; }); // 없으면 기본값 0 세팅.
    }
    setInitialReplies(prev => ({ ...prev, ...initialReplies }));
  };

  return (
    <ReplyCountContext.Provider value={{ replyCount, totalReplies, setInitialReplies }}>
      {children}
    </ReplyCountContext.Provider>
  )
}