import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Dev proxy — in Docker questi path sono gestiti da Nginx.
// In locale puntano direttamente ai backend.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api/catalog': { target: 'http://localhost:8082', changeOrigin: true },
      '/api/auth':    { target: 'http://localhost:8081', changeOrigin: true },
      '/api/user':    { target: 'http://localhost:8081', changeOrigin: true },
      '/api/admin':   { target: 'http://localhost:8080', changeOrigin: true },
    },
  },
});
