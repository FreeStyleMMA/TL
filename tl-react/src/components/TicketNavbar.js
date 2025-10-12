import { Link } from "react-router-dom";
import React, { useState } from 'react';
import './reset.css';
import './TicketNavbar.css';
export default function TicketNavbar() {
  const [activeCategory, setActiveCategory] = useState('concert');

  return (
    <nav id='nav'>
      {/* 카테고리 navbar는 카테고리 안의 상세 페이지(뮤지컬, 공연 전시 등) Link 해야함.*/}
      {[
        { name: '콘서트', path: 'concert' },
        { name: '뮤지컬', path: 'musical' },
        { name: '연극', path: 'theatre' },
      ].map((m) => (
        <Link
          key={m.path}
          className={`nav_text ${activeCategory === m.path ? 'active' : ''}`}
          to={`/ticket/${m.path}`}
          onClick={() => setActiveCategory(m.path)}
        >
          {m.name}
        </Link>
      ))}
    </nav >
  );
}