import React from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  Container,
  Paper,
  MenuItem,
} from '@mui/material';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useNavigate } from 'react-router-dom';
import { cadastrarJogador } from '../../services/api';

const validationSchema = yup.object({
  nome: yup.string().required('Nome é obrigatório'),
  email: yup
    .string()
    .email('Digite um email válido')
    .required('Email é obrigatório'),
  senha: yup
    .string()
    .min(6, 'A senha deve ter no mínimo 6 caracteres')
    .required('Senha é obrigatória'),
  nivel: yup.string().required('Nível é obrigatório'),
  localizacao: yup.string().required('Localização é obrigatória'),
});

const niveis = [
  'Iniciante',
  'Intermediário',
  'Avançado',
  'Profissional'
];

const Cadastro = () => {
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      nome: '',
      email: '',
      senha: '',
      nivel: '',
      localizacao: '',
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        await cadastrarJogador(values);
        navigate('/login');
      } catch (error) {
        console.error('Erro no cadastro:', error);
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
            Cadastro de Jogador
          </Typography>
          <form onSubmit={formik.handleSubmit}>
            <TextField
              fullWidth
              margin="normal"
              id="nome"
              name="nome"
              label="Nome"
              value={formik.values.nome}
              onChange={formik.handleChange}
              error={formik.touched.nome && Boolean(formik.errors.nome)}
              helperText={formik.touched.nome && formik.errors.nome}
            />
            <TextField
              fullWidth
              margin="normal"
              id="email"
              name="email"
              label="Email"
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
              value={formik.values.senha}
              onChange={formik.handleChange}
              error={formik.touched.senha && Boolean(formik.errors.senha)}
              helperText={formik.touched.senha && formik.errors.senha}
            />
            <TextField
              fullWidth
              margin="normal"
              id="nivel"
              name="nivel"
              select
              label="Nível"
              value={formik.values.nivel}
              onChange={formik.handleChange}
              error={formik.touched.nivel && Boolean(formik.errors.nivel)}
              helperText={formik.touched.nivel && formik.errors.nivel}
            >
              {niveis.map((nivel) => (
                <MenuItem key={nivel} value={nivel}>
                  {nivel}
                </MenuItem>
              ))}
            </TextField>
            <TextField
              fullWidth
              margin="normal"
              id="localizacao"
              name="localizacao"
              label="Localização"
              value={formik.values.localizacao}
              onChange={formik.handleChange}
              error={formik.touched.localizacao && Boolean(formik.errors.localizacao)}
              helperText={formik.touched.localizacao && formik.errors.localizacao}
            />
            <Box sx={{ mt: 3 }}>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mb: 2 }}
              >
                Cadastrar
              </Button>
              <Button
                fullWidth
                variant="text"
                onClick={() => navigate('/login')}
                sx={{ mb: 1 }}
              >
                Já tem uma conta? Faça login
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

export default Cadastro;