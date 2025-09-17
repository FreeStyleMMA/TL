import { Link } from 'react-router-dom';
import './Navbar.css';
import { useAuth } from '../pages/auth/AuthContext';

function Navbar() {
  const { member, signOut } = useAuth();

  return (
    <div className='NavbarLayout'>
      {/* nav 헤더(상위 우상단에 로그인, 마이페이지 등 작은 글씨로 링크) */}
      <div className='header'>
        <div className='headerLeft'>
          {/* 일단은 여백 공간 */}
        </div>
        <div className='headerMid'>
          {/* 일단은 여백 공간 2 */}
        </div>
        <div className='headerRight'>
          {member ? (
            <>
              <button onClick={signOut}>로그아웃</button>
            </>) : (
            <>
              <Link to="../signin">로그인</Link>
              <Link to="../signup" > 회원가입</Link>
            </>
          )}
        </div>
      </div>
      {/* nav body 홈페이지 로고 들어가고 커뮤니티/티켓 분기해주는 보통 아이콘 같은 
    글씨로 관리되는 부분 >> 일단 링크는 달 걸어둠*/}
      <div className='body'>
        <div className='bodyLeft'>
          <Link to='/'>홈페이지 로고</Link>
        </div>
        <div className='bodyMid'>
          <Link to='/community'>커뮤니티</Link>
          <Link to='/ticket'>티켓</Link>
        </div>
        <div className='bodyRight'>
          {/* 여기는 여백 */}
        </div>
        <hr />
      </div>
      {/* <div className='footer'>
        <hr/>
      </div> */}
    </div>
  )
}
export default Navbar;