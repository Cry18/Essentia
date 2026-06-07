import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';
import { getProfile, createShelf, deleteShelf, uploadProfileImage } from '../api/user.js';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './Profile.css';

export default function Profile() {
  const { user, logout } = useAuth();

  const [signature,       setSignature]       = useState(null);
  const [favorites,       setFavorites]       = useState([]);
  const [shelves,         setShelves]         = useState([]);
  const [profileImageUrl, setProfileImageUrl] = useState(null);
  const [loading,         setLoading]         = useState(true);
  const [newName,         setNewName]         = useState('');
  const [shelfMsg,        setShelfMsg]        = useState('');
  const [activeShelf,     setActiveShelf]     = useState(null);

  const ref = useScrollReveal([shelves, favorites, signature]);

  const flash = (text) => { setShelfMsg(text); setTimeout(() => setShelfMsg(''), 3000); };

  // Carica profilo completo all'avvio
  useEffect(() => {
    getProfile()
      .then(data => {
        setSignature(data.signature ?? null);
        setFavorites(data.favorites ?? []);
        setShelves(data.shelves ?? []);
        setProfileImageUrl(data.imageUrl ?? null);
      })
      .catch(() => flash('Impossibile caricare il profilo.'))
      .finally(() => setLoading(false));
  }, []);

  const handlePhotoUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    try {
      const url = await uploadProfileImage(file);
      setProfileImageUrl(url);
      flash('Foto profilo aggiornata!');
    } catch {
      flash('Impossibile caricare la foto.');
    }
  };

  const handleCreateShelf = async (e) => {
    e.preventDefault();
    if (!newName.trim()) return;
    try {
      const shelf = await createShelf(newName.trim());
      setShelves(s => [...s, shelf]);
      setNewName('');
      flash(`Scaffale "${shelf.name}" creato.`);
    } catch { flash('Impossibile creare lo scaffale.'); }
  };

  const handleDeleteShelf = async (shelfId) => {
    try {
      await deleteShelf(shelfId);
      setShelves(s => s.filter(sh => sh.id !== shelfId));
      if (activeShelf === shelfId) setActiveShelf(null);
      flash('Scaffale eliminato.');
    } catch { flash('Impossibile eliminare lo scaffale.'); }
  };

  const toggleShelf = (shelfId) => {
    setActiveShelf(prev => (prev === shelfId ? null : shelfId));
  };

  return (
    <div className="profile" ref={ref}>
      <div className="profile__header">
        <div className="container">
          <span className="label-eyebrow anim-fade">La tua collezione</span>
          <div className="profile__header-top anim-fade" style={{ animationDelay: '100ms' }}>
            <div className="profile__avatar-wrap">
              {profileImageUrl ? (
                <img src={profileImageUrl} alt="Foto profilo" className="profile__avatar" />
              ) : (
                <div className="profile__avatar profile__avatar--initials">
                  {user?.username?.[0]?.toUpperCase()}
                </div>
              )}
              <label className="profile__avatar-upload btn-text">
                Cambia foto
                <input type="file" accept="image/jpeg,image/png,image/webp"
                  onChange={handlePhotoUpload} hidden />
              </label>
            </div>
            <div>
              <h1 className="profile__title anim-reveal" style={{ animationDelay: '150ms' }}>
                {user?.username}
              </h1>
              <span className="profile__role anim-fade" style={{ animationDelay: '300ms' }}>
                {user?.role === 'ROLE_ADMIN' ? 'Amministratore' : 'Utente'}
              </span>
            </div>
          </div>
        </div>
      </div>

      <div className="container profile__body">

        {loading ? (
          <div className="spinner-wrap"><div className="spinner" /></div>
        ) : (
          <>
            {/* Signature Scent */}
            <section className="profile__section reveal">
              <h2 className="profile__section-title">Signature Scent</h2>
              {signature ? (
                <Link to={`/perfume/${signature.id}`} className="profile__signature">
                  <span className="label-eyebrow">{signature.brandName}</span>
                  <p className="profile__signature-name">{signature.name}</p>
                  <span className="profile__signature-arrow">→</span>
                </Link>
              ) : (
                <p className="empty-state" style={{ padding: 'var(--space-4) 0' }}>
                  Non hai ancora impostato una signature scent.
                  Vai alla pagina di un profumo e clicca "Imposta come Signature".
                </p>
              )}
            </section>

            <div className="section-divider">
              <span /><span className="ornament">◆</span><span />
            </div>

            {/* Preferiti */}
            <section className="profile__section reveal">
              <h2 className="profile__section-title">
                Preferiti{favorites.length > 0 && ` (${favorites.length})`}
              </h2>
              {favorites.length > 0 ? (
                <div className="profile__favorites">
                  {favorites.map(p => (
                    <Link key={p.id} to={`/perfume/${p.id}`} className="profile__fav-item">
                      <span className="label-eyebrow profile__fav-brand">{p.brandName}</span>
                      <span className="profile__fav-name">{p.name}</span>
                    </Link>
                  ))}
                </div>
              ) : (
                <p className="empty-state" style={{ padding: 'var(--space-4) 0' }}>
                  Nessun profumo tra i preferiti. Aggiungine dal catalogo.
                </p>
              )}
            </section>

            <div className="section-divider">
              <span /><span className="ornament">◆</span><span />
            </div>

            {/* Scaffali */}
            <section className="profile__section reveal">
              <h2 className="profile__section-title">I tuoi scaffali</h2>

              {shelfMsg && <p className="profile__msg">{shelfMsg}</p>}

              <form className="profile__new-shelf" onSubmit={handleCreateShelf}>
                <div className="input-group" style={{ flex: 1 }}>
                  <label className="input-label">Nome nuovo scaffale</label>
                  <input
                    className="input-field"
                    value={newName}
                    onChange={e => setNewName(e.target.value)}
                    placeholder="es. Fragranze estive"
                    maxLength={30}
                  />
                </div>
                <button type="submit" className="btn btn-ghost">Crea</button>
              </form>

              {shelves.length === 0 ? (
                <p className="empty-state" style={{ padding: 'var(--space-8) 0' }}>
                  La tua collezione è ancora silenziosa. Crea il primo scaffale.
                </p>
              ) : (
                <div className="profile__shelves">
                  {shelves.map(sh => (
                    <div key={sh.id} className="shelf-item">
                      <div className="shelf-item__header">
                        <span className="shelf-item__name">{sh.name}</span>
                        <div className="shelf-item__actions">
                          <button className="btn btn-text" onClick={() => toggleShelf(sh.id)}>
                            {activeShelf === sh.id ? 'Chiudi' : 'Vedi'}
                          </button>
                          <button className="btn btn-text shelf-item__delete"
                            onClick={() => handleDeleteShelf(sh.id)}>
                            Elimina
                          </button>
                        </div>
                      </div>

                      {activeShelf === sh.id && (
                        <div className="shelf-item__contents anim-slide-up">
                          {sh.perfumes?.length > 0 ? (
                            <ul className="shelf-item__list">
                              {sh.perfumes.map(p => (
                                <li key={p.id} className="shelf-item__perfume">
                                  <Link to={`/perfume/${p.id}`} className="shelf-item__perfume-link">
                                    {p.brandName && (
                                      <span className="shelf-item__perfume-brand">{p.brandName}</span>
                                    )}
                                    <span className="shelf-item__perfume-name">{p.name}</span>
                                  </Link>
                                </li>
                              ))}
                            </ul>
                          ) : (
                            <p className="shelf-item__empty">Scaffale vuoto.</p>
                          )}
                        </div>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </section>

            <div className="section-divider">
              <span /><span className="ornament">◆</span><span />
            </div>

            {/* Info account */}
            <section className="profile__section reveal">
              <h2 className="profile__section-title">Account</h2>
              <div className="profile__account-info">
                <p><span className="label-eyebrow">Username</span>{user?.username}</p>
                <p><span className="label-eyebrow">Ruolo</span>{user?.role}</p>
              </div>
              <button className="btn btn-ghost profile__logout" onClick={logout}>
                Disconnetti
              </button>
            </section>
          </>
        )}

      </div>
    </div>
  );
}
