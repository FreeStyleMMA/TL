import React, { createContext, useState, useEffect } from "react";
import axios from 'axios';

export const LikeContext = createContext();

export function LikeProvider({ children, postNo, memberId }) {
  const [liked, setLiked] = useState({});
  const [totalLikes, setTotalLikes] = useState(0);


  const handleLike = async (memberId, postNo) => {
    try {
      const response = await axios.post(`http://localhost:8080/post/handleLike`,
        { postNo: postNo, memberId: memberId }
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
  useEffect(() => {
    const fetchLikes = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/post/countLikes?postNo=${postNo}`
        );
        setLiked(response.data.newLiked);
        setTotalLikes(response.data.totalLikes);
      } catch (error) {
        console.log(error);
      }
    };

    fetchLikes();
  }, []);

  return (
    <LikeContext.Provider value={{ handleLike, liked, totalLikes }}>
      {children}
    </LikeContext.Provider>
  )
}

