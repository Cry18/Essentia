import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getPerfume } from '../api/catalog.js';
import { addFavorite, setSignature, createReview, updateReview, deleteReview, getProfile, addToShelf } from '../api/user.js';
import { deleteAdminReview } from '../api/admin.js';
import { useAuth } from '../context/AuthContext.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import ConfirmModal from '../components/ConfirmModal.jsx';
import './PerfumeDetail.css';

const NOTE_LABELS = { 1: 'Testa', 2: 'Cuore', 3: 'Fondo' };
const SEASON_LABELS = ['', 'Primavera', 'Estate', 'Autunno', 'Inverno'];
const PLACEHOLDER = '/assets/images/placeholder-perfume.jpg';

export default function PerfumeDetail() {
  const { id } = useParams();
  const { isAuthenticated, isAdmin, user } = useAuth();
  const [perfume, setPerfume]     = useState(null);
  const [loading, setLoading]     = useState(true);
  const [error,   setError]       = useState(null);
  const [msg,     setMsg]         = useState('');
  const [showReviewForm, setShowReviewForm] = useState(false);
  // Aggiungi a scaffale
  const [shelves,       setShelves]       = useState([]);
  const [showShelfPicker, setShowShelfPicker] = useState(false);
  const [selectedShelfId, setSelectedShelfId] = useState('');
  const [shelvesLoaded,   setShelvesLoaded]   = useState(false);
  // Modifica recensione
  const [editingReview, setEditingReview] = useState(null);
  // Conferma eliminazione recensione
  const [deleteReviewId, setDeleteReviewId] = useState(null);
  const ref = useScrollReveal([perfume]);

  useEffect(() => {
    setLoading(true);
    getPerfume(id)
      .then(setPerfume)
      .catch(() => setError('Fragranza non trovata.'))
      .finally(() => setLoading(false));
  }, [id]);

  const flash = (text) => { setMsg(text); setTimeout(() => setMsg(''), 3000); };

  const handleFavorite  = () => addFavorite(+id).then(() => flash('Aggiunto ai preferiti.'));
  const handleSignature = () => setSignature(+id).then(() => flash('Signature aggiornato.'));

  // ── Scaffale ─────────────────────────────────────────────────────────────
  const handleOpenShelfPicker = () => {
    if (!shelvesLoaded) {
      getProfile()
        .then(data => {
          const list = data.shelves ?? [];
          setShelves(list);
          if (list.length > 0) setSelectedShelfId(String(list[0].id));
          setShelvesLoaded(true);
        })
        .catch(() => flash('Impossibile caricare gli scaffali.'));
    }
    setShowShelfPicker(v => !v);
  };

  const handleAddToShelf = async () => {
    if (!selectedShelfId) return;
    try {
      await addToShelf(+selectedShelfId, +id);
      flash('Profumo aggiunto allo scaffale!');
      setShowShelfPicker(false);
    } catch (err) {
      const status = err.response?.status;
      if (status === 409) flash('Profumo già presente in questo scaffale.');
      else flash('Errore. Riprova.');
    }
  };

  // ── Edit/delete recensione ────────────────────────────────────────────────
  const confirmDeleteReview = (reviewId) => setDeleteReviewId(reviewId);

  const handleDeleteReview = async () => {
    const reviewId = deleteReviewId;
    setDeleteReviewId(null);
    try {
      /* L'admin usa l'endpoint admin (nessun controllo proprietà),
         l'utente normale usa quello user (controlla proprietà) */
      if (isAdmin) {
        await deleteAdminReview(reviewId);
      } else {
        await deleteReview(reviewId);
      }
      flash('Recensione eliminata.');
      getPerfume(id).then(setPerfume);
    } catch {
      flash('Errore durante l\'eliminazione.');
    }
  };

  const handleUpdateReview = async (e) => {
    e.preventDefault();
    const fd = new FormData(e.target);
    try {
      await updateReview(editingReview.id, {
        title:       fd.get('title'),
        description: fd.get('description'),
        vote:        +fd.get('vote'),
        seasonality: +fd.get('seasonality'),
        sillage:     +fd.get('sillage'),
        longevity:   +fd.get('longevity'),
        gender:      fd.get('gender') === 'true',
      });
      flash('Recensione aggiornata!');
      setEditingReview(null);
      getPerfume(id).then(setPerfume);
    } catch {
      flash('Errore durante l\'aggiornamento. Controlla i campi e riprova.');
    }
  };

  const handleReview = async (e) => {
    e.preventDefault();
    const fd = new FormData(e.target);
    try {
      await createReview(+id, {
        title:       fd.get('title'),
        description: fd.get('description'),
        vote:        +fd.get('vote'),
        seasonality: +fd.get('seasonality'),
        sillage:     +fd.get('sillage'),
        longevity:   +fd.get('longevity'),
        gender:      fd.get('gender') === 'true',
      });
      flash('Recensione pubblicata!');
      setShowReviewForm(false);
      getPerfume(id).then(setPerfume);
    } catch (err) {
      const status = err.response?.status;
      if (status === 409) {
        flash('Hai già recensito questo profumo.');
      } else if (status === 400) {
        flash('Dati non validi. Controlla i campi e riprova.');
      } else {
        flash('Errore durante la pubblicazione. Riprova.');
      }
    }
  };

  if (loading) return <div className="spinner-wrap"><div className="spinner" /></div>;
  if (error)   return <p className="empty-state">{error}</p>;
  if (!perfume) return null;

  const notesByType = [1, 2, 3].map(t => ({
    label: NOTE_LABELS[t],
    notes: (perfume.notes ?? []).filter(n => n.type === t),
  }));

  return (
    <div className="detail" ref={ref}>

      {/* Header */}
      <div className="detail__header">
        <div className="container detail__header-inner">

          {/* Colonna testo */}
          <div className="detail__header-text">
            <Link to={`/catalog?brand=${encodeURIComponent(perfume.brand)}`}
              className="label-eyebrow anim-fade detail__brand-link">
              {perfume.brand}
            </Link>
            <h1 className="detail__title anim-reveal" style={{ animationDelay: '150ms' }}>
              {perfume.name}
            </h1>
            {perfume.parfumers?.length > 0 && (
              <p className="detail__parfumers anim-fade" style={{ animationDelay: '300ms' }}>
                Creato da{' '}
                {perfume.parfumers.map((p, i) => (
                  <span key={p}>
                    <Link to={`/catalog?parfumer=${encodeURIComponent(p)}`}
                      className="detail__parfumer-link">{p}</Link>
                    {i < perfume.parfumers.length - 1 && ', '}
                  </span>
                ))}
              </p>
            )}
            {isAuthenticated && (
              <>
                <div className="detail__actions anim-fade" style={{ animationDelay: '450ms' }}>
                  <button className="btn btn-ghost" onClick={handleFavorite}>+ Preferiti</button>
                  <button className="btn btn-ghost" onClick={handleSignature}>Imposta come Signature</button>
                  <button className="btn btn-ghost" onClick={handleOpenShelfPicker}>
                    {showShelfPicker ? '× Chiudi' : '+ Scaffale'}
                  </button>
                </div>
                {showShelfPicker && (
                  <div className="detail__shelf-picker anim-fade">
                    {!shelvesLoaded ? (
                      <span>Caricamento scaffali…</span>
                    ) : shelves.length === 0 ? (
                      <p>
                        Nessuno scaffale creato.{' '}
                        <Link to="/profile" className="btn-text">Creane uno nel profilo →</Link>
                      </p>
                    ) : (
                      <div className="detail__shelf-picker__inner">
                        <select
                          value={selectedShelfId}
                          onChange={e => setSelectedShelfId(e.target.value)}
                          className="input-field"
                        >
                          {shelves.map(s => (
                            <option key={s.id} value={String(s.id)}>{s.name}</option>
                          ))}
                        </select>
                        <button className="btn btn-filled" onClick={handleAddToShelf}>
                          Aggiungi
                        </button>
                      </div>
                    )}
                  </div>
                )}
              </>
            )}
            {msg && <p className="detail__flash">{msg}</p>}
          </div>

          {/* Colonna immagine — blend naturale con lo sfondo */}
          <div className="detail__img-wrap anim-fade" style={{ animationDelay: '200ms' }}>
            <img
              src={perfume.imageUrl ?? PLACEHOLDER}
              alt={perfume.name}
              className="detail__img"
            />
          </div>

        </div>
      </div>

      <div className="container detail__body">

        {/* Descrizione */}
        <section className="detail__section reveal">
          <h2 className="detail__section-title">La fragranza</h2>
          <p className="detail__description">{perfume.description}</p>
        </section>

        <div className="section-divider">
          <span /><span className="ornament">◆</span><span />
        </div>

        {/* Piramide olfattiva */}
        <section className="detail__section reveal">
          <h2 className="detail__section-title">Piramide olfattiva</h2>
          <div className="pyramid">
            {notesByType.map(({ label, notes }) =>
              notes.length > 0 && (
                <div key={label} className="pyramid__row">
                  <span className="pyramid__label label-eyebrow">{label}</span>
                  <div className="pyramid__tags">
                    {notes.map(n => (
                      <Link key={n.name}
                        to={`/note/${n.id}`}
                        className="tag tag--link">{n.name}</Link>
                    ))}
                  </div>
                </div>
              )
            )}
          </div>
        </section>

        <div className="section-divider">
          <span /><span className="ornament">◆</span><span />
        </div>

        {/* Recensioni */}
        <section className="detail__section reveal">
          <h2 className="detail__section-title">Recensioni</h2>

          {isAuthenticated && (
            <button
              className="btn btn-ghost detail__review-btn"
              onClick={() => setShowReviewForm(v => !v)}
            >
              {showReviewForm ? 'Annulla' : 'Scrivi una recensione'}
            </button>
          )}

          {showReviewForm && (
            <form className="review-form anim-slide-up" onSubmit={handleReview}>
              <div className="review-form__grid">
                <div className="input-group" style={{ gridColumn: '1/-1' }}>
                  <label className="input-label">Titolo</label>
                  <input name="title" className="input-field" required placeholder="Il mio parere su…" />
                </div>
                <div className="input-group" style={{ gridColumn: '1/-1' }}>
                  <label className="input-label">Descrizione</label>
                  <textarea name="description" className="input-field review-form__textarea"
                    required placeholder="Condividi la tua esperienza olfattiva…" />
                </div>
                <div className="input-group">
                  <label className="input-label">Voto (1–5)</label>
                  <input name="vote" type="number" min="1" max="5" className="input-field" required />
                </div>
                <div className="input-group">
                  <label className="input-label">Stagionalità</label>
                  <select name="seasonality" className="input-field review-form__select">
                    {SEASON_LABELS.slice(1).map((s, i) => (
                      <option key={s} value={i + 1}>{s}</option>
                    ))}
                  </select>
                </div>
                <div className="input-group">
                  <label className="input-label">Sillage (1–5)</label>
                  <input name="sillage" type="number" min="1" max="5" className="input-field" required />
                </div>
                <div className="input-group">
                  <label className="input-label">Longevità (1–5)</label>
                  <input name="longevity" type="number" min="1" max="5" className="input-field" required />
                </div>
                <div className="input-group">
                  <label className="input-label">Genere</label>
                  <select name="gender" className="input-field review-form__select">
                    <option value="false">Femminile</option>
                    <option value="true">Maschile</option>
                  </select>
                </div>
              </div>
              <button type="submit" className="btn btn-filled">Pubblica recensione</button>
            </form>
          )}

          {perfume.reviews?.length > 0 ? (
            <div className="reviews">
              {perfume.reviews.map((r, i) => {
                const isOwn = r.authorUsername && user?.username
                  && r.authorUsername === user.username;

                // Modalità modifica — mostra form pre-compilato al posto della card
                if (editingReview?.id === r.id) {
                  return (
                    <form key={r.id ?? i} className="review-form review-form--edit anim-slide-up"
                      onSubmit={handleUpdateReview}>
                      <p className="input-label" style={{ marginBottom: 'var(--space-4)' }}>
                        Modifica la tua recensione
                      </p>
                      <div className="review-form__grid">
                        <div className="input-group" style={{ gridColumn: '1/-1' }}>
                          <label className="input-label">Titolo</label>
                          <input name="title" className="input-field" required
                            defaultValue={r.title} placeholder="Il mio parere su…" />
                        </div>
                        <div className="input-group" style={{ gridColumn: '1/-1' }}>
                          <label className="input-label">Descrizione</label>
                          <textarea name="description"
                            className="input-field review-form__textarea" required
                            defaultValue={r.description} />
                        </div>
                        <div className="input-group">
                          <label className="input-label">Voto (1–5)</label>
                          <input name="vote" type="number" min="1" max="5"
                            className="input-field" required defaultValue={r.vote} />
                        </div>
                        <div className="input-group">
                          <label className="input-label">Stagionalità</label>
                          <select name="seasonality" className="input-field review-form__select"
                            defaultValue={r.seasonality}>
                            {SEASON_LABELS.slice(1).map((s, idx) => (
                              <option key={s} value={idx + 1}>{s}</option>
                            ))}
                          </select>
                        </div>
                        <div className="input-group">
                          <label className="input-label">Sillage (1–5)</label>
                          <input name="sillage" type="number" min="1" max="5"
                            className="input-field" required defaultValue={r.sillage} />
                        </div>
                        <div className="input-group">
                          <label className="input-label">Longevità (1–5)</label>
                          <input name="longevity" type="number" min="1" max="5"
                            className="input-field" required defaultValue={r.longevity} />
                        </div>
                        <div className="input-group">
                          <label className="input-label">Genere</label>
                          <select name="gender" className="input-field review-form__select"
                            defaultValue={String(r.gender)}>
                            <option value="false">Femminile</option>
                            <option value="true">Maschile</option>
                          </select>
                        </div>
                      </div>
                      <div style={{ display: 'flex', gap: 'var(--space-4)' }}>
                        <button type="submit" className="btn btn-filled">Salva modifiche</button>
                        <button type="button" className="btn btn-ghost"
                          onClick={() => setEditingReview(null)}>Annulla</button>
                      </div>
                    </form>
                  );
                }

                // Card normale
                return (
                  <article key={r.id ?? i}
                    className={`review reveal${isOwn ? ' review--own' : ''}`}>
                    <header className="review__header">
                      <span className="review__user">
                        {r.user}
                        {isOwn && <span className="review__badge">Tu</span>}
                      </span>
                      <span className="review__vote">{r.vote}/5</span>
                    </header>
                    <h4 className="review__title">{r.title}</h4>
                    <p className="review__body">{r.description}</p>
                    <footer className="review__meta">
                      <span className="tag">{SEASON_LABELS[r.seasonality]}</span>
                      <span className="tag">Sillage {r.sillage}/5</span>
                      <span className="tag">Longevità {r.longevity}/5</span>
                      <span className="tag">{r.gender ? 'Maschile' : 'Femminile'}</span>
                      {(isOwn || isAdmin) && (
                        <div className="review__actions">
                          {isOwn && (
                            <button className="btn btn-text"
                              onClick={() => setEditingReview(r)}>Modifica</button>
                          )}
                          <button className="btn btn-text review__delete-btn"
                            onClick={() => confirmDeleteReview(r.id)}>Elimina</button>
                        </div>
                      )}
                    </footer>
                  </article>
                );
              })}
            </div>
          ) : (
            <p className="empty-state" style={{ padding: 'var(--space-8) 0' }}>
              Ancora nessuna recensione. Sii il primo a condividere la tua impressione.
            </p>
          )}
        </section>

      </div>

      {deleteReviewId !== null && (
        <ConfirmModal
          message="Vuoi eliminare questa recensione? L'operazione non è reversibile."
          onConfirm={handleDeleteReview}
          onCancel={() => setDeleteReviewId(null)}
        />
      )}
    </div>
  );
}
