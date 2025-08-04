import React, { useState, useEffect } from 'react';
import {
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Button,
} from '@mui/material';
import { listarQuadras } from '../../services/api';
import { useNavigate } from 'react-router-dom';
import ReservaQuadra from '../ReservaQuadra';
import { useAuth } from '../../contexts/AuthContext'; // Corrija o import

const ListaQuadras = () => {
  const [quadras, setQuadras] = useState([]);
  const [quadraSelecionada, setQuadraSelecionada] = useState(null);
  const [openReserva, setOpenReserva] = useState(false);
  const navigate = useNavigate();
  const { jogador } = useAuth();

  const buscarQuadras = async () => {
    try {
      const response = await listarQuadras();
      setQuadras(response);
    } catch (error) {
      console.error('Erro ao buscar quadras:', error);
    }
  };

  useEffect(() => {
    buscarQuadras();
  }, []);

  const handleReservaClick = (quadra) => {
    setQuadraSelecionada(quadra);
    setOpenReserva(true);
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Lista de Quadras
      </Typography>
      {jogador?.papel === 'PROFESSOR' && (
        <Button
          variant="contained"
          color="primary"
          sx={{ mb: 2 }}
          onClick={() => navigate('/quadras/cadastro')}
        >
          Nova Quadra
        </Button>
      )}
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nome</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Localização</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {quadras.map((quadra) => (
              <TableRow key={quadra.id}>
                <TableCell>{quadra.nome}</TableCell>
                <TableCell>{quadra.tipo}</TableCell>
                <TableCell>{quadra.localizacao}</TableCell>
                <TableCell>
                  {quadra.disponivel ? 'Disponível' : 'Indisponível'}
                </TableCell>
                <TableCell>
                  {jogador?.papel === 'ALUNO' && quadra.disponivel && (
                    <Button
                      variant="contained"
                      size="small"
                      onClick={() => handleReservaClick(quadra)}
                    >
                      Reservar
                    </Button>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <ReservaQuadra
        quadra={quadraSelecionada}
        open={openReserva}
        onClose={() => setOpenReserva(false)}
        onSuccess={() => {
          buscarQuadras();
          setOpenReserva(false);
        }}
      />
    </Container>
  );
};

export default ListaQuadras;