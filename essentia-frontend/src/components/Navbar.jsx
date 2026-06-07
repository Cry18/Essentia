import { useState, useEffect } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';
import './Navbar.css';

export default function Navbar() {
  const [scrolled,     setScrolled]     = useState(false);
  const [menuOpen,     setMenuOpen]     = useState(false);
  const { isAuthenticated, isAdmin, user, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 80);
    window.addEventListener('scroll', onScroll, { passive: true });
    return () => window.removeEventListener('scroll', onScroll);
  }, []);

  const handleLogout = () => {
    logout();
    setMenuOpen(false);
    navigate('/');
  };

  return (
    <header className={`navbar ${scrolled ? 'navbar--scrolled' : ''}`}>
      <div className="navbar__inner">

        {/* Logo */}
        <Link to="/" className="navbar__logo" onClick={() => setMenuOpen(false)}>
          ESSENTIA
        </Link>

        {/* Nav links — desktop */}
        <nav className="navbar__links">
          <NavLink to="/catalog"   className={({ isActive }) => isActive ? 'active' : ''}>Catalogo</NavLink>
          <NavLink to="/brands"    className={({ isActive }) => isActive ? 'active' : ''}>Brand</NavLink>
          <NavLink to="/parfumers" className={({ isActive }) => isActive ? 'active' : ''}>Profumieri</NavLink>
          <NavLink to="/notes"     className={({ isActive }) => isActive ? 'active' : ''}>Note</NavLink>
        </nav>

        {/* Azioni — desktop */}
        <div className="navbar__actions">
          {isAuthenticated ? (
            <>
              <Link to="/profile" className="navbar__user">
                {user?.username}
              </Link>
              {isAdmin && (
                <Link to="/admin" className="navbar__admin-badge" title="Pannello di amministrazione">
                  ⚙ Gestione
                </Link>
              )}
              <button className="btn btn-ghost navbar__btn" onClick={handleLogout}>
                Esci
              </button>
            </>
          ) : (
            <>
              <Link to="/login"    className="btn btn-ghost navbar__btn">Accedi</Link>
              <Link to="/register" className="btn btn-filled navbar__btn">Registrati</Link>
            </>
          )}
        </div>

        {/* Hamburger — mobile */}
        <button
          className={`navbar__hamburger ${menuOpen ? 'open' : ''}`}
          onClick={() => setMenuOpen(v => !v)}
          aria-label="Menu"
        >
          <span /><span /><span />
        </button>
      </div>

      {/* Menu mobile */}
      {menuOpen && (
        <div className="navbar__mobile-menu anim-fade">
          <NavLink to="/catalog"   onClick={() => setMenuOpen(false)}>Catalogo</NavLink>
          <NavLink to="/brands"    onClick={() => setMenuOpen(false)}>Brand</NavLink>
          <NavLink to="/parfumers" onClick={() => setMenuOpen(false)}>Profumieri</NavLink>
          <NavLink to="/notes"     onClick={() => setMenuOpen(false)}>Note</NavLink>
          {isAuthenticated ? (
            <>
              <NavLink to="/profile" onClick={() => setMenuOpen(false)}>Profilo</NavLink>
              {isAdmin && <NavLink to="/admin" onClick={() => setMenuOpen(false)}>⚙ Gestione</NavLink>}
              <button onClick={handleLogout}>Esci</button>
            </>
          ) : (
            <>
              <NavLink to="/login"    onClick={() => setMenuOpen(false)}>Accedi</NavLink>
              <NavLink to="/register" onClick={() => setMenuOpen(false)}>Registrati</NavLink>
            </>
          )}
        </div>
      )}
    </header>
  );
}
