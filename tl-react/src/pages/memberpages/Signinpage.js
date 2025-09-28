import SignIn from './SignIn';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';

export default function SignInPage() {
  const nav = useNavigate();
  const location = useLocation();
  const { loginComplete } = useAuth();

  const handleLogin = (userData) => {
    loginComplete(userData);

    // 로그인 시 이동할 경로 (이전 페이지)
    const from = location.state?.from?.pathname || '/';
    nav(from, { replace: true });
  }


  return (

    <>
      <SignIn onSignIn={handleLogin} />
    </>
  )
}''