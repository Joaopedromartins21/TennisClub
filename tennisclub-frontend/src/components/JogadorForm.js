import React, { useState } from 'react';
import {
    Box,
    TextField,
    Button,
    Container,
    Typography,
    MenuItem
} from '@mui/material';
import api from '../services/api';

const JogadorForm = () => {
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        nivel: '',
        localizacao: '',
        papel: 'ALUNO'
    });

    const niveis = ['iniciante', 'intermediário', 'avançado'];
    const papeis = ['ALUNO', 'PROFESSOR'];

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/jogadores', formData);
            alert('Jogador cadastrado com sucesso!');
            setFormData({
                nome: '',
                email: '',
                senha: '',
                nivel: '',
                localizacao: '',
                papel: 'ALUNO'
            });
        } catch (error) {
            alert('Erro ao cadastrar jogador');
            console.error(error);
        }
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ mt: 4 }}>
                <Typography variant="h4" gutterBottom>
                    Cadastro de Jogador
                </Typography>
                <form onSubmit={handleSubmit}>
                    {/* ... outros campos ... */}
                    <TextField
                        fullWidth
                        select
                        label="Papel"
                        name="papel"
                        value={formData.papel}
                        onChange={handleChange}
                        margin="normal"
                        required
                    >
                        {papeis.map((papel) => (
                            <MenuItem key={papel} value={papel}>
                                {papel}
                            </MenuItem>
                        ))}
                    </TextField>
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        sx={{ mt: 2 }}
                        fullWidth
                    >
                        Cadastrar
                    </Button>
                </form>
            </Box>
        </Container>
    );
};

export default JogadorForm;