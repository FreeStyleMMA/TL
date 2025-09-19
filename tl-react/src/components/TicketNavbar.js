import { Link } from "react-router-dom";
import './reset.css';
import './TicketNavbar.css';
export default function TicketNavbar() {
  return (
    <nav id='nav'>
      {/* 카테고리 navbar는 카테고리 안의 상세 페이지(뮤지컬, 공연 전시 등) Link 해야함.*/}
      <Link id='nav_text' to="/ticket/concert">콘서트</Link>
      <Link id='nav_text' to="/ticket/musical">뮤지컬</Link>
      <Link id='nav_text' to="/ticket/theatre">연극</Link>
    </nav >
  );
}