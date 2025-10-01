import axios from 'axios';
import React, { createContext, useState, useEffect } from "react";
import { useAuth } from '../pages/auth/AuthContext';

export const FavoriteContext = createContext();

export function FavoriteProvider({ children, memberId, perId }) {

  const [liked, setLiked] = useState({});
  const [totalLIkes, setTotalLikes] = useState(0);


  const handleFavorite = async (memberId, perId) => {
    try {
      const response = await axios.post(`http://localhost:8080/favorite/handleFavorite`,
        null,
        { params: { memberId, perId } }
      );
      setLiked(prev => ({ ...prev, [perId]: response.data.newLiked }));
      console.log("liked: ", response.data.newLiked)
      setTotalLikes("totalLikes: ", response.data.totalLikes)
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    const fetchLiked = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/favorite/checkFavorite?memberId=${memberId}&perId=${perId}`);
        setLiked(response.data); // 서버에서 { per_id: 0/1 } 형태로 반환
      } catch (error) {
        console.log(error);
      }
    }
    fetchLiked();
  }, [memberId]);



  return (
    <FavoriteContext.Provider value={{ handleFavorite, liked }}>
      {children}
    </FavoriteContext.Provider>
  )
}