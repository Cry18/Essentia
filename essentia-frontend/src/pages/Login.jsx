import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { login as apiLogin } from '../api/auth.js';
import { useAuth } from '../context/AuthContext.jsx';
import './Auth.css';

export default function Login() {
  const { login } = useAuth();
  const navigate  = useNavigate();
  const [error,   setError]   = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(''); setLoading(true);
    const fd = new FormData(e.target);
    try {
      const data = await apiLogin({ username: fd.get('username'), password: fd.get('password') });
      login(data);
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message ?? 'Credenziali non corrette. Riprova.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card anim-slide-up">
        <div className="auth-card__header">
          <span className="label-eyebrow">Bentornato</span>
          <h1 className="auth-card__title">Accedi</h1>
        </div>

        <form className="auth-form" onSubmit={handleSubmit}>
          <div className="input-group">
            <label className="input-label" htmlFor="username">Username</label>
            <input id="username" name="username" className="input-field" required autoFocus />
          </div>
          <div className="input-group">
            <label className="input-label" htmlFor="password">Password</label>
            <input id="password" name="password" type="password" className="input-field" required />
          </div>

          {error && <p className="auth-error">{error}</p>}

          <button type="submit" className="btn btn-ghost auth-submit" disabled={loading}>
            {loading ? 'Accesso in corso…' : 'Accedi'}
          </button>
        </form>

        <p className="auth-switch">
          Non hai un account? <Link to="/register">Registrati</Link>
        </p>
      </div>
    </div>
  );
}
