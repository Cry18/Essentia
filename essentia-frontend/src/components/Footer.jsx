import { Link } from 'react-router-dom';
import './Footer.css';

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer__inner container">

        <div className="footer__logo">ESSENTIA</div>

        <nav className="footer__nav">
          <Link to="/catalog">Catalogo</Link>
          <Link to="/brands">Brand</Link>
          <Link to="/parfumers">Profumieri</Link>
          <Link to="/login">Accedi</Link>
        </nav>

        <div className="footer__divider section-divider">
          <span /><span className="ornament">◆</span><span />
        </div>

        <p className="footer__copy">
          © {new Date().getFullYear()} Essentia — Progetto di tirocinio universitario.
          Design &amp; frontend interamente realizzati da{' '}
          <a href="https://claude.ai" target="_blank" rel="noreferrer">Claude</a>.
        </p>
      </div>
    </footer>
  );
}
