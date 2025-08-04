import React from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  Container,
  Paper,
} from '@mui/material';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useNavigate } from 'react-router-dom';
import { loginJogador } from '../../services/api';
import { useAuth } from '../../contexts/AuthContext';

const validationSchema = yup.object({
  email: yup
    .string()
    .email('Digite um email válido')
    .required('Email é obrigatório'),
  senha: yup
    .string()
    .required('Senha é obrigatória'),
});

const Login = () => {
  const navigate = useNavigate();
  const { setJogador } = useAuth();

  const formik = useFormik({
    initialValues: {
      email: '',
      senha: '',
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        const response = await loginJogador(values);
        setJogador(response);
        navigate('/dashboard');
      } catch (error) {
        console.error('Erro no login:', error);
      }
    },
  });

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Paper elevation={3} sx={{ padding: 4, width: '100%' }}>
          <Typography component="h1" variant="h5" align="center" gutterBottom>
            Login
          </Typography>
          <form onSubmit={formik.handleSubmit}>
            <TextField
              fullWidth
              margin="normal"
              id="email"
              name="email"
              label="Email"
              autoComplete="email"
              value={formik.values.email}
              onChange={formik.handleChange}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
            />
            <TextField
              fullWidth
              margin="normal"
              id="senha"
              name="senha"
              label="Senha"
              type="password"
              autoComplete="current-password"
              value={formik.values.senha}
              onChange={formik.handleChange}
              error={formik.touched.senha && Boolean(formik.errors.senha)}
              helperText={formik.touched.senha && formik.errors.senha}
            />
            <Box sx={{ mt: 3 }}>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mb: 2 }}
              >
                Entrar
              </Button>
              <Button
                fullWidth
                variant="text"
                onClick={() => navigate('/cadastro')}
                sx={{ mb: 1 }}
              >
                Não tem uma conta? Cadastre-se
              </Button>
              <Button
                fullWidth
                variant="text"
                onClick={() => navigate('/')}
              >
                Voltar para a página inicial
              </Button>
            </Box>
          </form>
        </Paper>
      </Box>
    </Container>
  );
};

export default Login;