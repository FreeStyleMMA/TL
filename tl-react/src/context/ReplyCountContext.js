import React, { createContext, useState } from "react";
import axios from 'axios'

export const ReplyCountContext = createContext();

export function ReplyCountProvider({ children }) {

  const [totalReplies, setTotalReplies] = useState(0);

  const replyCount = async (postNo) => {
    try {
      const response = await axios.get(`http://localhost:8080/reply/count?originNo=${postNo}`);
      setTotalReplies(prev => ({ ...prev, [postNo]: response.data }));
    }
    catch (error) {
      console.log(error);
    }
  };

  return (
    <ReplyCountContext.Provider value={{ replyCount, totalReplies }}>
      {children}
    </ReplyCountContext.Provider>
  )
}