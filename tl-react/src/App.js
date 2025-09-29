import './App.css';
import { BrowserRouter, Routes, Route, Outlet, Navigate } from 'react-router-dom';
import Homepage from './pages/Hompage'
import Navbar from './components/Navbar';
//Ticketpage 연결 모음
import TicketHomepage from './pages/ticketpages/TicketHomepage';
import TicketNavbar from './components/TicketNavbar';
import TicketConcertpage from './pages/ticketpages/TicketConcertpage';
import TicketMusicalpage from './pages/ticketpages/TicketMusicalpage';
import TicketTheatrepage from './pages/ticketpages/TicketTheatrepage';
import TicketInfopage from './pages/ticketpages/TicketInfopage';

// Comunitypage 연결 모음
import ComNavbar from './components/ComNavbar';
import ComHomePage from './pages/communitypages/ComHomePage';
import ReviewBoard from './pages/communitypages/ReviewBoard';
import FreeBoard from './pages/communitypages/FreeBoard';
import PostWrite from './pages/communitypages/PostWrite';
import PostDetailPage from './pages/communitypages/PostDetailPage';

//memberpage 연결 모음
import SignIn from './pages/memberpages/Signinpage';
import SignUp from './pages/memberpages/Signuppage';
import Mypage from './pages/memberpages/Mypage';
import { AuthProvider } from './pages/auth/AuthContext';
import ProtectedRoute from './pages/auth/ProtectedRoute';
import { useAuth } from './pages/auth/AuthContext';
import { ReplyCountProvider } from "./context/ReplyCountContext";
import { LikeProvider } from './context/LikeContext';
import { DeleteProvider } from './context/DeleteContext';
import { FavoriteProvider } from './context/FavoriteContext';
export default function App() {
// ticket 레이 아웃 적용
function TicketLayout() {
  return (
    <div>
      <TicketNavbar />
      <div className="content">
        <Outlet />
      </div>
    </div>
  );
}

// community 레이아웃 적용
function CommunityLayout() {
  return (
    <div>
      <ComNavbar />
      <div className="content">
        <Outlet />
      </div>
    </div>
  );
}

  return (
    <AuthProvider>
      <FavoriteProvider>
        <DeleteProvider>
          <LikeProvider>
            <ReplyCountProvider>
              <BrowserRouter>
                {/* 최상단 공통 Navbar */}
                <Navbar />
                <AppRoutes />
              </BrowserRouter>
            </ReplyCountProvider>
          </LikeProvider>
        </DeleteProvider>
      </FavoriteProvider>
    </AuthProvider>
  );


//함수 호출을 위한 Route 페이지 분기
function AppRoutes() {
  const { member } = useAuth(); // member 정보 받아오기
  console.log("멤버?", member);

  return (

    <Routes>
      {/* 기본 홈 */}
      <Route path="/" element={<Homepage />} />
      <Route path="signup" element={<SignUp />} />
      <Route path='signin' element={<SignIn />} />
      <Route path='mypage' element={<Mypage />} />

      {/* Ticket 전용 레이아웃 */}
      <Route path="ticket" element={<TicketLayout />}>
        <Route index element={<TicketHomepage />} />
        <Route path="concert" element={<TicketConcertpage />} />
        <Route path="musical" element={<TicketMusicalpage />} />
        <Route path="theatre" element={<TicketTheatrepage />} />
        <Route path="info" element={<TicketInfopage />} />
      </Route>

      {/* Community 전용 레이아웃 */}
      <Route path="community" element={<CommunityLayout />}>
        <Route index element={<ComHomePage />} />
        <Route path="comHome" element={<ComHomePage />} />
        <Route path="reviewBoard" element={<ReviewBoard />} />
        <Route path="freeBoard" element={<FreeBoard />} />
        {/* 권한에 따른 접근 제한 예시 */}
        <Route path="posting" element=
          {<ProtectedRoute member={member} allowedRoles={['ADMIN', 'MEMBER']}><PostWrite /></ProtectedRoute>} />

        <Route path="reviewBoard/posts/:no" element={<><PostDetailPage /></>} /> {/* PostDetailPage -- Review 컴포넌트들 동적 연결 */}
        <Route path="freeBoard/posts/:no" element={<><PostDetailPage /></>} /> {/* PostDetailPage -- Review 컴포넌트들 동적 연결 */}
        <Route path="comHome/posts/:no" element={<><PostDetailPage /></>} /> {/* PostDetailPage -- Review 컴포넌트들 동적 연결 */}
      </Route>
    </Routes>
  );


}
}