import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [jogador, setJogador] = useState(() => {
    const storedJogador = localStorage.getItem('jogador');
    return storedJogador ? JSON.parse(storedJogador) : null;
  });

  return (
    <AuthContext.Provider value={{ jogador, setJogador }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth deve ser usado dentro de um AuthProvider');
  }
  return context;
};

export default AuthContext;