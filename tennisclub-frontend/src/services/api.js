import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

export const cadastrarJogador = async (dadosJogador) => {
    try {
        const response = await api.post('/jogadores', dadosJogador);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Erro ao cadastrar jogador';
    }
};

export const loginJogador = async (credentials) => {
    try {
        const response = await api.post('/jogadores/login', credentials);
        localStorage.setItem('jogador', JSON.stringify(response.data));
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Erro ao fazer login';
    }
};

export const logout = () => {
    localStorage.removeItem('jogador');
};

export const cadastrarQuadra = async (dadosQuadra) => {
  try {
    const response = await api.post('/quadras', dadosQuadra);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Erro ao cadastrar quadra';
  }
};

export const listarQuadras = async () => {
  try {
    const response = await api.get('/quadras');
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Erro ao listar quadras';
  }
};

export const listarJogadores = async () => {
  try {
    const response = await api.get('/jogadores');
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Erro ao listar jogadores';
  }
};

export const realizarReserva = async (dados) => {
  const response = await api.post('/reservas', dados);
  return response.data;
};

export const listarReservasPorQuadra = async (quadraId) => {
  const response = await api.get(`/reservas/quadra/${quadraId}`);
  return response.data;
};

export const listarReservasPorJogador = async (jogadorId) => {
  const response = await api.get(`/reservas/jogador/${jogadorId}`);
  return response.data;
};

export const listarTodasReservas = async () => {
  const response = await api.get('/reservas/todas');
  return response.data;
};


export default api;
