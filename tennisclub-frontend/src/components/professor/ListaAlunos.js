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
} from '@mui/material';
import { listarJogadores } from '../../services/api';

const ListaAlunos = () => {
  const [alunos, setAlunos] = useState([]);

  useEffect(() => {
    const buscarAlunos = async () => {
      try {
        const jogadores = await listarJogadores();
        // Filtra apenas os jogadores que são alunos
        setAlunos(jogadores.filter(jogador => jogador.papel === 'ALUNO'));
      } catch (error) {
        console.error('Erro ao buscar alunos:', error);
      }
    };

    buscarAlunos();
  }, []);

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Lista de Alunos
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nome</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Nível</TableCell>
              <TableCell>Localização</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {alunos.map((aluno) => (
              <TableRow key={aluno.id}>
                <TableCell>{aluno.nome}</TableCell>
                <TableCell>{aluno.email}</TableCell>
                <TableCell>{aluno.nivel}</TableCell>
                <TableCell>{aluno.localizacao}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default ListaAlunos;
