import axios from 'axios';
import React, { createContext } from 'react';
export const DeleteContext = createContext();

export function DeleteProvider({ children }) {
  const handlePostDelete = async (postNo) => {
    try {
      const response = await axios.delete(`http://localhost:8080/post/delete?no=${postNo}`);
      console.log(response.data.message);
    } catch (error) {
      console.error("delete error: ", error)
    }
    window.history.back();
  }
  const handleReplyDelete = async (replyNo, originNo) => {
    try {
      const response = await axios.delete(`http://localhost:8080/reply/delete?no=${replyNo}&originNo=${originNo}`);
      console.log(response.data.message);
    } catch (error) {
      console.error("delete error: ", error)
    }
  }
  return (
    <DeleteContext.Provider value={{ handlePostDelete, handleReplyDelete }}>
      {children}
    </DeleteContext.Provider>
  )
}