import axios from 'axios';
import React, { createContext, useState, useEffect } from "react";
import { useAuth } from '../pages/auth/AuthContext';

export const FavoriteContext = createContext();

export function FavoriteProvider({ children, memberId, perId }) {

  const [liked, setLiked] = useState({});


  const handleFavorite = async (memberId, per_id) => {
    try {
      const response = await axios.post(`http://localhost:8080/favorite/handleFavorite`,
        { memberId, per_id }
      );
      setLiked(prev => ({ ...prev, [per_id]: response.data.newLiked }));
      console.log("liked: ", response.data.newLiked)
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    const fetchLiked = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/favorite/checkFavorite?memberId=${memberId}&perId=${perId}`);
        setLiked(response.data); // 서버에서 { per_id: true/false } 형태로 반환
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