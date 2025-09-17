import React, { useContext, useState } from "react";


const AuthContext = React.createContext();

export function AuthProvider({ children }) {
  //session에 user가져오거나  없으면 null
  const [member, setMember] = useState(JSON.parse(sessionStorage.getItem("member")) || null);

  const loginComplete = (userData) => {
    setMember(userData);
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