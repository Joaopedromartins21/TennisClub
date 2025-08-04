import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

const PrivateRoute = ({ children }) => {
  const { jogador } = useAuth();

  if (!jogador) {
    return <Navigate to="/login" />;
  }

  return children;
};

export default PrivateRoute;