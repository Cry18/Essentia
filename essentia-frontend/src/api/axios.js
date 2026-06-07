import axios from 'axios';

const api = axios.create({ baseURL: '/' });

// Inietta il JWT su ogni richiesta se presente
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('essentia_token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export default api;
