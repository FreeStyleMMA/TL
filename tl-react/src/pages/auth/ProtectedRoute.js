import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ member, allowedRoles, children }) {

  if (!member) {
    // 로그인 안 되어 있으면 로그인 페이지로 이동
    return <Navigate to="/signin" replace />;
  }

  if (!allowedRoles.includes(member.role)) {
    // 권한 없으면 접근 불가 페이지로 이동
    return <Navigate to="/src/pages/auth/Unauthorized" replace />;
  }

  // 권한 있음 → 실제 페이지 렌더링
  return children;

}