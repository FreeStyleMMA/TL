import React, { useContext, useState } from "react";


const AuthContext = React.createContext();

export function AuthProvider({ children }) {
  //session에 user가져오거나  없으면 null
  const [member, setMember] = useState(
    //세션에 저장된 user 정보 "member"로 가져오기
    JSON.parse(sessionStorage.getItem("member")) || null);

  const loginComplete = (accessToken) => {
    console.log("AuthContext에 찍히는 token", accessToken);
    //  accessToken 디코딩.
    const decoded = JSON.parse(atob(accessToken.split('.')[1]));

    const userData = {// 디코딩된 Token 정보 재정렬
      memberId: decoded.sub,
      role: decoded.role,
      // accessToken: accessToken
    };
    console.log("세션에 저장되는 userData:", userData);
    setMember(userData); // member로 SET 하고 세션에 저장. 
    sessionStorage.setItem("member", JSON.stringify(userData));
  };

  const signOut = () => {
    setMember(null);
    sessionStorage.removeItem("member");
  }

  return (
    <AuthContext.Provider value={{ member, loginComplete, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);