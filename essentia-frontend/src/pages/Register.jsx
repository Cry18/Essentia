import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { register as apiRegister } from '../api/auth.js';
import { useAuth } from '../context/AuthContext.jsx';
import './Auth.css';

export default function Register() {
  const { login } = useAuth();
  const navigate  = useNavigate();
  const [error,   setError]   = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(''); setLoading(true);
    const fd = new FormData(e.target);
    try {
      const data = await apiRegister({
        username: fd.get('username'),
        password: fd.get('password'),
        name:     fd.get('name'),
        surname:  fd.get('surname'),
      });
      login(data);
      navigate('/profile');
    } catch (err) {
      setError(err.response?.data?.message ?? 'Registrazione non riuscita. Riprova.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card anim-slide-up">
        <div className="auth-card__header">
          <span className="label-eyebrow">Crea il tuo profilo</span>
          <h1 className="auth-card__title">Registrati</h1>
        </div>

        <form className="auth-form" onSubmit={handleSubmit}>
          <div className="auth-form__row">
            <div className="input-group">
              <label className="input-label" htmlFor="name">Nome</label>
              <input id="name" name="name" className="input-field" required />
            </div>
            <div className="input-group">
              <label className="input-label" htmlFor="surname">Cognome</label>
              <input id="surname" name="surname" className="input-field" required />
            </div>
          </div>
          <div className="input-group">
            <label className="input-label" htmlFor="username">Username</label>
            <input id="username" name="username" className="input-field" required autoFocus
              minLength={3} maxLength={50} />
          </div>
          <div className="input-group">
            <label className="input-label" htmlFor="password">Password</label>
            <input id="password" name="password" type="password" className="input-field"
              required minLength={6} />
            <span className="input-error" style={{ color: 'var(--color-text-muted)' }}>
              Minimo 6 caratteri
            </span>
          </div>

          {error && <p className="auth-error">{error}</p>}

          <button type="submit" className="btn btn-ghost auth-submit" disabled={loading}>
            {loading ? 'Registrazione in corso…' : 'Crea account'}
          </button>
        </form>

        <p className="auth-switch">
          Hai già un account? <Link to="/login">Accedi</Link>
        </p>
      </div>
    </div>
  );
}
