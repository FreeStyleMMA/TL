import { Link } from 'react-router-dom';
import './Navbar.css';
import './reset.css';
import { useAuth } from '../pages/auth/AuthContext';

function Navbar() {
  const { member, signOut } = useAuth();

  return (
    <div id="nav_layout">
      <div id='top'>
        {/* nav 헤더(상위 우상단에 로그인, 마이페이지 등 작은 글씨로 링크) */}
        <div className='nav_content'>
          {member ? (
            <>
              <button onClick={signOut} className='top_text'>| &nbsp;로그아웃&nbsp; |</button>
              <Link to="../mypage" className='top_text' > &nbsp;마이페이지&nbsp;|</Link> {/*찐 장바구니로 수정해야함*/}
            </>) : (
            <>
              <Link to="../signin" className='top_text'>| &nbsp;로그인&nbsp; |</Link>
              <Link to="../signup" className='top_text'>  &nbsp;회원가입&nbsp; |</Link>
            </>
          )}
        </div>
      </div>
      <div id='body'>
        <Link id='homepage_name' to='/'>TL</Link>
        <div id='bodyMid'>
          <Link className='body_text' to='/community'>커뮤니티</Link>
          <Link className='body_text' to='/ticket'>공연/전시</Link>
        </div>
        <hr />
      </div>
    </div>
  )
}
export default Navbar;