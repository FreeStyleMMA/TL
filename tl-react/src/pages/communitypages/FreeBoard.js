import { Link } from "react-router-dom";

export default function FreeBoard() {

  return (
    <div>
      자유게시판
      <div>
        <Link to='../posting'>글쓰기</Link>
      </div>
    </div>
  )
}