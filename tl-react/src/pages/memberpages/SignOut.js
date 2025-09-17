import './Logout.css';
import { useAuth } from '../pages/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';


export default function SignOut() {

  const nav = useNavigate();
  const { member, signOut } = useAuth();

  const handleSignout = (e) => {
    e.preventDefault();
    signOut();
  }

  useEffect(() => {
    if (!member) {
      nav('/');
    }
  }, [member, nav]);

  return (
    <div>
      <button className="button" onClick={handleSignout}>로그아웃</button>
    </div >
  )
}