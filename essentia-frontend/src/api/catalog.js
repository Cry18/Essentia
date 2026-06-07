import api from './axios.js';

// ── Perfumi ────────────────────────────────────────────────────────────────
export const getPerfumes = (params = {}) =>
  api.get('/api/catalog/perfumes', { params }).then(r => r.data);

export const getPerfume = (id) =>
  api.get(`/api/catalog/perfume/${id}`).then(r => r.data);

// ── Brand ──────────────────────────────────────────────────────────────────
export const getBrands = (params = {}) =>
  api.get('/api/catalog/brands/', { params }).then(r => r.data);

export const getBrand = (id) =>
  api.get(`/api/catalog/brand/${id}`).then(r => r.data);

// ── Parfumer ───────────────────────────────────────────────────────────────
export const getParfumers = (params = {}) =>
  api.get('/api/catalog/parfumers/', { params }).then(r => r.data);

export const getParfumer = (id) =>
  api.get(`/api/catalog/parfumer/${id}`).then(r => r.data);

// ── Note ───────────────────────────────────────────────────────────────────
export const getNotes = (params = {}) =>
  api.get('/api/catalog/perfumenotes/', { params }).then(r => r.data);

export const getNote = (id) =>
  api.get(`/api/catalog/perfumenote/${id}`).then(r => r.data);
