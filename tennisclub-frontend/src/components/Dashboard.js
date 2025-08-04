import React, { useState, useEffect } from 'react';
import {
  Container,
  Grid,
  Paper,
  Typography,
  Button,
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  CircularProgress
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { listarReservasPorJogador, listarTodasReservas } from '../services/api';
import { format } from 'date-fns';
import ptBR from 'date-fns/locale/pt-BR';

const Dashboard = () => {
  const navigate = useNavigate();
  const { jogador } = useAuth();
  const [reservas, setReservas] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const buscarReservas = async () => {
      try {
        const response = jogador.papel === 'PROFESSOR'
          ? await listarTodasReservas()
          : await listarReservasPorJogador(jogador.id);
        setReservas(response);
      } catch (error) {
        console.error('Erro ao buscar reservas:', error);
      } finally {
        setLoading(false);
      }
    };

    buscarReservas();
  }, [jogador]);

  const TabelaReservas = () => {
    if (loading) {
      return (
        <Box display="flex" justifyContent="center" my={3}>
          <CircularProgress />
        </Box>
      );
    }

    if (reservas.length === 0) {
      return (
        <Typography variant="body1" sx={{ textAlign: 'center', my: 3 }}>
          Nenhuma reserva encontrada.
        </Typography>
      );
    }

    return (
      <TableContainer component={Paper} sx={{ mt: 3 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Quadra</TableCell>
              <TableCell>Data e Hora</TableCell>
              <TableCell>Status</TableCell>
              {jogador.papel === 'PROFESSOR' && <TableCell>Jogadores</TableCell>}
            </TableRow>
          </TableHead>
          <TableBody>
            {reservas.map((reserva) => (
              <TableRow key={reserva.id}>
                <TableCell>{reserva.quadra.nome}</TableCell>
                <TableCell>
                  {format(new Date(reserva.dataHora), "dd/MM/yyyy 'às' HH:mm", { locale: ptBR })}
                </TableCell>
                <TableCell>{reserva.status}</TableCell>
                {jogador.papel === 'PROFESSOR' && (
                  <TableCell>
                    {reserva.jogadores.map(j => j.nome).join(', ')}
                  </TableCell>
                )}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  };

  const ProfessorDashboard = () => (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h6" gutterBottom>
            Todas as Reservas
          </Typography>
          <TabelaReservas />
        </Paper>
      </Grid>
      <Grid item xs={12} md={6}>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h6" gutterBottom>
            Gerenciar Quadras
          </Typography>
          <Button
            fullWidth
            variant="contained"
            onClick={() => navigate('/quadras')}
            sx={{ mt: 2 }}
          >
            Ver Quadras
          </Button>
          <Button
            fullWidth
            variant="outlined"
            onClick={() => navigate('/quadras/cadastro')}
            sx={{ mt: 2 }}
          >
            Cadastrar Nova Quadra
          </Button>
        </Paper>
      </Grid>
    </Grid>
  );

  const AlunoDashboard = () => (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h6" gutterBottom>
            Minhas Reservas
          </Typography>
          <TabelaReservas />
        </Paper>
      </Grid>
      <Grid item xs={12}>
        <Paper sx={{ p: 3 }}>
          <Typography variant="h6" gutterBottom>
            Quadras Disponíveis
          </Typography>
          <Button
            fullWidth
            variant="contained"
            onClick={() => navigate('/quadras')}
            sx={{ mt: 2 }}
          >
            Ver Quadras
          </Button>
        </Paper>
      </Grid>
    </Grid>
  );

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Box sx={{ mb: 4 }}>
        <Typography variant="h4" gutterBottom>
          Bem-vindo, {jogador.nome}!
        </Typography>
        <Typography variant="subtitle1" color="text.secondary">
          {jogador.papel === 'PROFESSOR' ? 'Professor' : 'Aluno'}
        </Typography>
      </Box>
      {jogador.papel === 'PROFESSOR' ? <ProfessorDashboard /> : <AlunoDashboard />}
    </Container>
  );
};

export default Dashboard;