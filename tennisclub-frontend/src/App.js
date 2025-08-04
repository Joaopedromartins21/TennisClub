import React from 'react';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import LandingPage from './components/LandingPage';
import Login from './components/auth/Login';
import Cadastro from './components/auth/Cadastro';
import Dashboard from './components/Dashboard';
import QuadraForm from './components/professor/QuadraForm';
import ListaQuadras from './components/professor/ListaQuadras';
import ListaAlunos from './components/professor/ListaAlunos';
import PrivateRoute from './components/auth/PrivateRoute';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <Router>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/cadastro" element={<Cadastro />} />
            <Route
              path="/dashboard"
              element={
                <PrivateRoute>
                  <Dashboard />
                </PrivateRoute>
              }
            />
            <Route
              path="/quadras"
              element={
                <PrivateRoute>
                  <ListaQuadras />
                </PrivateRoute>
              }
            />
            <Route
              path="/quadras/cadastro"
              element={
                <PrivateRoute>
                  <QuadraForm />
                </PrivateRoute>
              }
            />
            <Route
              path="/alunos"
              element={
                <PrivateRoute>
                  <ListaAlunos />
                </PrivateRoute>
              }
            />
          </Routes>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;