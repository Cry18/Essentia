import { Link } from 'react-router-dom';
import './NotFound.css';

export default function NotFound() {
  return (
    <div className="notfound">
      <span className="notfound__code anim-shimmer">404</span>
      <h1 className="notfound__title anim-reveal">Pagina non trovata</h1>
      <p className="notfound__body anim-fade" style={{ animationDelay: '300ms' }}>
        La pagina che cerchi non esiste o è stata spostata.
      </p>
      <Link to="/" className="btn btn-ghost anim-fade" style={{ animationDelay: '500ms' }}>
        Torna alla home
      </Link>
    </div>
  );
}
