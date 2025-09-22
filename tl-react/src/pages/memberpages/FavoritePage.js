import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useAuth } from '../auth/AuthContext';


export default function FavoritePage() {
  const { member } = useAuth();
  const [per_id, setPer_id] = useState(0);

  const getFavorite = async (memberId) => {
    try {
      const response = await axios.get(`http://localhost:8080/favorite/handleFavorite?memberId=${member.memberId}`)
      setPer_id(response.data.per_id);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div>
      마이페이지// 여기서 per 객체 받아올때는 그 쪽 controller 함수 끌어와서 or 참고해서 쓰기.
      <ul>

      </ul>
    </div>
  )
}