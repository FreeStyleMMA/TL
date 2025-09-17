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
import ReviewBoard from './pages/communitypages/ReviewBoard';
import FreeBoard from './pages/communitypages/FreeBoard';
import PostWrite from './pages/communitypages/PostWrite';
import PostDetailPage from './pages/communitypages/PostDetailPage';
import ReplyList from './pages/communitypages/ReplyList';
import ReplyWrite from './pages/communitypages/ReplyWrite';

//memberpage 연결 모음
import SignIn from './pages/memberpages/SignInPage';
import SignUp from './pages/memberpages/SignUpPage'

import { AuthProvider } from './pages/auth/AuthContext';
import ProtectedRoute from './pages/auth/ProtectedRoute';
import { useAuth } from './pages/auth/AuthContext';


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

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        {/* 최상단 공통 Navbar */}
        <Navbar />
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  );
}

function AppRoutes() {
  const member = useAuth(); // 이제 AuthProvider 내부이므로 안전
  console.log("멤버?", member);

  return (

    <Routes>
      {/* 기본 홈 */}
      <Route path="/" element={<Homepage />} />
      <Route path="signup" element={<SignUp />} />
      <Route path='signin' element={<SignIn />} />

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
        <Route index element={<Navigate to="reviewBoard" />} />
        <Route path="reviewBoard" element={<ReviewBoard />} />
        <Route path="freeBoard" element={<FreeBoard />} />
        <Route path="posting" element=
          {<ProtectedRoute member={member} allowedRoles={['ADMIN', 'MEMBER']}><PostWrite /></ProtectedRoute>} />
        <Route path="reviewBoard/posts/:no" element={<><PostDetailPage /></>} /> {/* PostDetailPage -- Review 컴포넌트들 동적 연결 */}
      </Route>
    </Routes>
  );


}
