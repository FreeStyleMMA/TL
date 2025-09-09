import './App.css';
import { BrowserRouter, Routes, Route, Outlet } from 'react-router-dom';
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
import ComHomepage from './pages/communitypages/ComHomepage';
import ComNavbar from './components/ComNavbar';
import ComConcertpage from './pages/communitypages/ComConcertpage';
import ComMusicalpage from './pages/communitypages/ComMusicalpage';
import ComTheatrepage from './pages/communitypages/ComTheatrepage';

//memberpage 연결 모음
import Signinpage from './pages/memberpages/Signinpage';
import Signuppage from './pages/memberpages/Signuppage'


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
    <BrowserRouter>
      {/* 최상단 공통 Navbar */}
      <Navbar />

      <Routes>
        {/* 기본 홈 */}
        <Route path="/" element={<Homepage />} />

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
          <Route index element={<ComHomepage />} />
          <Route path="concert" element={<ComConcertpage />} />
          <Route path="musical" element={<ComMusicalpage />} />
          <Route path="theatre" element={<ComTheatrepage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}