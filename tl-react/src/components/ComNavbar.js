import { Link } from "react-router-dom";

export default function ComNavbar() {
  return (
    <nav style={{
      display: "flex", gap: "20px", marginLeft: "550px", height: "60px"
    }}>
      {/* 카테고리 navbar는 카테고리 안의 상세 페이지(뮤지컬, 공연 전시 등) Link 해야함.*/}
      < Link to="/community/reviewBoard" > 리뷰 게시판</Link>
      <Link to="/community/freeBoard">자유게시판</Link>
    </ nav>
  );
}