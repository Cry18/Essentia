import api from './axios.js';

// ── Brand ──────────────────────────────────────────────────────────────────
export const createBrand = (data) =>
  api.post('/api/admin/add/brand', data).then(r => r.data);

export const updateBrand = (id, data) =>
  api.put(`/api/admin/edit/brand/${id}`, data).then(r => r.data);

export const deleteBrand = (id) =>
  api.delete(`/api/admin/delete/brand/${id}`).then(r => r.data);

// ── Parfumer ───────────────────────────────────────────────────────────────
export const createParfumer = (data) =>
  api.post('/api/admin/add/parfumer', data).then(r => r.data);

export const updateParfumer = (id, data) =>
  api.put(`/api/admin/edit/parfumer/${id}`, data).then(r => r.data);

export const deleteParfumer = (id) =>
  api.delete(`/api/admin/delete/parfumer/${id}`).then(r => r.data);

// ── Note olfattive ─────────────────────────────────────────────────────────
export const createNote = (data) =>
  api.post('/api/admin/add/perfumenote', data).then(r => r.data);

export const updateNote = (id, data) =>
  api.put(`/api/admin/edit/perfumenote/${id}`, data).then(r => r.data);

export const deleteNote = (id) =>
  api.delete(`/api/admin/delete/perfumenote/${id}`).then(r => r.data);

// ── Perfume ────────────────────────────────────────────────────────────────
export const createPerfume = (data) =>
  api.post('/api/admin/add/perfume', data).then(r => r.data);

export const updatePerfume = (id, data) =>
  api.put(`/api/admin/edit/perfume/${id}`, data).then(r => r.data);

export const deletePerfume = (id) =>
  api.delete(`/api/admin/delete/perfume/${id}`).then(r => r.data);

// ── Recensioni (admin) ────────────────────────────────────────────────────
export const deleteAdminReview = (id) =>
  api.delete(`/api/admin/delete/review/${id}`).then(r => r.data);

// ── Upload immagini ────────────────────────────────────────────────────────
export const uploadImage = (file) => {
  const fd = new FormData();
  fd.append('file', file);
  return api.post('/api/admin/upload', fd).then(r => r.data);
};

// ── Statistiche ────────────────────────────────────────────────────────────
export const getMostDesired    = () =>
  api.get('/api/admin/statistics/most-desired').then(r => r.data);

export const getMostAppreciated = () =>
  api.get('/api/admin/statistics/most-appreciated').then(r => r.data);
