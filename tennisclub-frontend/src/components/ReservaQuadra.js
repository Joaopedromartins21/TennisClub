import axios from 'axios';
import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
} from '@mui/material';
import { useAuth } from '../contexts/AuthContext';
import { realizarReserva } from '../services/api';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
});

const ReservaQuadra = ({ quadra, open, onClose, onSuccess }) => {
  const [dataHora, setDataHora] = useState('');
  const { jogador } = useAuth();

  const handleReserva = async () => {
    try {
      await realizarReserva({
        quadraId: quadra.id,
        jogadorId: jogador.id,
        dataHora: new Date(dataHora).toISOString()
      });
      onSuccess();
      onClose();
    } catch (error) {
      console.error('Erro ao realizar reserva:', error);
      alert(error.response?.data || 'Erro ao realizar reserva');
    }
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Reservar Quadra {quadra?.nome}</DialogTitle>
      <DialogContent>
        <TextField
          label="Data e Hora"
          type="datetime-local"
          value={dataHora}
          onChange={(e) => setDataHora(e.target.value)}
          sx={{ width: '100%', mt: 2 }}
          InputLabelProps={{
            shrink: true,
          }}
          inputProps={{
            min: new Date().toISOString().slice(0, 16),
          }}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancelar</Button>
        <Button onClick={handleReserva} variant="contained" color="primary">
          Confirmar Reserva
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ReservaQuadra;