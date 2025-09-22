import axios from 'axios';
import React, { createContext, useState, useEffect } from "react";
import { useAuth } from '../auth/AuthContext';
import { AuthProvider } from '../auth/AuthContext';

export const FavoriteContext = createContext();

export function FavoriteProvider({ children, memberId, per_id }) {
  const [liked, setLiked] = useState({});


  const handleFavorite = async (memberId, per_id) => {
    try {
      const response = await axios.post(`http://localhost:8080/favorite/handleFavorite`,
        { per_id: per_id, memberId: memberId }
      );
      setLiked(prev => ({ ...prev, [per_id]: response.data.newLiked }));
      console.log("liked: ", response.data.newLiked)
    } catch (error) {
      console.log(error)
    }
  }


  return (
    <FavoriteContext.Provider value={{ handleFavorite, liked }}>
      {children}
    </FavoriteContext.Provider>
  )
}