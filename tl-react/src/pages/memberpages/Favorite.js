import axios from 'axios';
import React, { useState } from 'react';
import { useAuth } from '../auth/AuthContext';
import { AuthProvider } from '../auth/AuthContext';

export default function FavoritePage() {
  const { member } = useAuth();


  const handleSubmit = () => {


  }

  return (
    <>
      <form onSubmit={handleSubmit}>


      </form>
    </>
  )
}