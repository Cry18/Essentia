import api from './axios.js';

// ── Profile ────────────────────────────────────────────────────────────────
export const getProfile = () =>
  api.get('/api/user/profile').then(r => r.data);

// ── Favorites ──────────────────────────────────────────────────────────────
export const addFavorite    = (perfumeId) =>
  api.put('/api/user/favorites/add/',    null, { params: { perfumeId } }).then(r => r.data);

export const removeFavorite = (perfumeId) =>
  api.put('/api/user/favorites/remove/', null, { params: { perfumeId } }).then(r => r.data);

// ── Signature ──────────────────────────────────────────────────────────────
export const setSignature = (perfumeId) =>
  api.put('/api/user/signature', null, { params: { perfumeId } }).then(r => r.data);

// ── Shelf ──────────────────────────────────────────────────────────────────
export const getShelf       = (shelfId) =>
  api.get('/api/user/shelf/detail/', { params: { shelfId } }).then(r => r.data);

export const createShelf    = (shelfName) =>
  api.post('/api/user/shelf/create/', null, { params: { shelfName } }).then(r => r.data);

export const deleteShelf    = (shelfId) =>
  api.delete('/api/user/shelf/delete/', { params: { shelfId } }).then(r => r.data);

export const addToShelf     = (shelfId, perfumeId) =>
  api.put('/api/user/shelf/add/',    null, { params: { shelfId, perfumeId } }).then(r => r.data);

export const removeFromShelf = (shelfId, perfumeId) =>
  api.put('/api/user/shelf/remove/', null, { params: { shelfId, perfumeId } }).then(r => r.data);

// ── Review ─────────────────────────────────────────────────────────────────
export const createReview = (perfumeId, data) =>
  api.post('/api/user/review/create/', data, { params: { perfumeId } }).then(r => r.data);

export const updateReview = (reviewId, data) =>
  api.put('/api/user/review/update/', data, { params: { reviewId } }).then(r => r.data);

export const deleteReview = (reviewId) =>
  api.delete('/api/user/review/delete/', { params: { reviewId } }).then(r => r.data);

// ── Upload foto profilo ─────────────────────────────────────────────────────
export const uploadProfileImage = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return api.post('/api/user/profile/image', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  }).then(r => r.data);
};
