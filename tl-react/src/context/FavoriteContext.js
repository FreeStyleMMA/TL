import axios from 'axios';
import React, { createContext, useState } from "react";

export const FavoriteContext = createContext();

export function FavoriteProvider({ children }) {
  const [liked, setLiked] = useState({});  // perNum: liked 상태 (0 또는 1)
  const [totalLikes, setTotalLikes] = useState(0);

  const handleFavorite = async (memberId, perNum) => {
    try {
      const response = await axios.post(`http://localhost:8080/favorite/handleFavorite`, null, {
        params: { memberId, perNum }
      });
      setLiked(prev => ({ ...prev, [perNum]: response.data.liked }));
      setTotalLikes(response.data.totalLikes);
    } catch (error) {
      console.log(error);
    }
  }

  const fetchLiked = async (memberId, perNum) => {
    try {
      const response = await axios.get(`http://localhost:8080/favorite/checkFavorite`, {
        params: { memberId, perNum }
      });
      setLiked(prev => ({ ...prev, [perNum]: response.data }));
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <FavoriteContext.Provider value={{ handleFavorite, liked, fetchLiked, totalLikes }}>
      {children}
    </FavoriteContext.Provider>
  )
}