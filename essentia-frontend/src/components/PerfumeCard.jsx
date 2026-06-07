import { Link } from 'react-router-dom';
import './Cards.css';

const PLACEHOLDER = '/assets/images/placeholder-perfume.jpg';

export default function PerfumeCard({ perfume }) {
  const { id, name, brand, notes, parfumers, imageUrl } = perfume;

  // notes può essere null nella lista (solo il dettaglio le include)
  const topNotes = (notes ?? [])
    .filter(n => n.type === 1)
    .slice(0, 3)
    .map(n => n.name);

  // fallback: se non ci sono note di testa, mostra i parfumer
  const subtitle = topNotes.length > 0
    ? topNotes.join(' · ')
    : (parfumers ?? []).slice(0, 2).join(', ');

  return (
    <Link to={`/perfume/${id}`} className="card reveal">
      <div className="card__img-wrap">
        <img src={imageUrl ?? PLACEHOLDER} alt={name} loading="lazy" />
        <div className="card__img-overlay" />
      </div>
      <div className="card__body">
        <span className="label-eyebrow card__brand">{brand}</span>
        <h3 className="card__name">{name}</h3>
        {subtitle && (
          <p className="card__notes">{subtitle}</p>
        )}
      </div>
      <div className="card__accent" />
    </Link>
  );
}
