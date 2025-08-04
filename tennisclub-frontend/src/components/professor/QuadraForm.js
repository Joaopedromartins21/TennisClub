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
import { cadastrarQuadra } from '../../services/api';

const tipos = ['Saibro', 'Grama', 'Dura'];

const validationSchema = yup.object({
  nome: yup.string().required('Nome é obrigatório'),
  tipo: yup.string().required('Tipo é obrigatório'),
  localizacao: yup.string().required('Localização é obrigatória'),
});

const QuadraForm = () => {
  const formik = useFormik({
    initialValues: {
      nome: '',
      tipo: '',
      localizacao: '',
      disponivel: true,
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        await cadastrarQuadra(values);
        formik.resetForm();
        // Adicione feedback de sucesso aqui
      } catch (error) {
        console.error('Erro ao cadastrar quadra:', error);
      }
    },
  });

  return (
    <Container component="main" maxWidth="xs">
      <Paper elevation={3} sx={{ padding: 4, marginTop: 4 }}>
        <Typography component="h1" variant="h5" align="center" gutterBottom>
          Cadastrar Quadra
        </Typography>
        <form onSubmit={formik.handleSubmit}>
          <TextField
            fullWidth
            margin="normal"
            name="nome"
            label="Nome da Quadra"
            value={formik.values.nome}
            onChange={formik.handleChange}
            error={formik.touched.nome && Boolean(formik.errors.nome)}
            helperText={formik.touched.nome && formik.errors.nome}
          />
          <TextField
            fullWidth
            margin="normal"
            name="tipo"
            select
            label="Tipo de Quadra"
            value={formik.values.tipo}
            onChange={formik.handleChange}
            error={formik.touched.tipo && Boolean(formik.errors.tipo)}
            helperText={formik.touched.tipo && formik.errors.tipo}
          >
            {tipos.map((tipo) => (
              <MenuItem key={tipo} value={tipo}>
                {tipo}
              </MenuItem>
            ))}
          </TextField>
          <TextField
            fullWidth
            margin="normal"
            name="localizacao"
            label="Localização"
            value={formik.values.localizacao}
            onChange={formik.handleChange}
            error={formik.touched.localizacao && Boolean(formik.errors.localizacao)}
            helperText={formik.touched.localizacao && formik.errors.localizacao}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Cadastrar Quadra
          </Button>
        </form>
      </Paper>
    </Container>
  );
};

export default QuadraForm;
