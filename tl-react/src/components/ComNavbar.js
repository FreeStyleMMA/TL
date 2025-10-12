import { Link } from "react-router-dom";
import './ComNavbar.css';
import './reset.css';

export default function ComNavbar() {
  return (
    <nav id='nav'>
      {/* 카테고리 navbar는 카테고리 안의 상세 페이지(뮤지컬, 공연 전시 등) Link 해야함.*/}
      <Link className='nav_text' to="/community">커뮤니티</Link>
      < Link className='nav_text' to="/community/reviewBoard" > 후기</Link>
      <Link className='nav_text' to="/community/freeBoard">잡담</Link>
    </ nav>
  );
}