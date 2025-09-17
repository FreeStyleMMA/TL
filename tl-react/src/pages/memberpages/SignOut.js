import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import './Logout.css';
import { useAuth } from '../pages/AuthContext';

export default function SignOut() {

  const nav = useNavigate();
  const { signOut } = useAuth();


  return (
    <div>
      <form className="signout" onSubmit={signOut}>
        <button type="submit" className="button">로그아웃</button>
      </form>
      {message && <p>{message}</p>}
    </div >
  )
}