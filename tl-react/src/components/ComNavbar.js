import { Link } from "react-router-dom";

export default function ComNavbar() {
  return (
    <nav style={{
      display: "flex", gap: "20px", marginLeft: "550px", height: "60px"
    }}>
      {/* 카테고리 navbar는 카테고리 안의 상세 페이지(뮤지컬, 공연 전시 등) Link 해야함.*/}
      < Link to="/community/concert" > 콘서트</Link>
      <Link to="/community/musical">뮤지컬</Link>
      <Link to="/community/theatre">연극</Link>
    </ nav>
  );
}