import { Link } from 'react-router-dom';
import './Cards.css';

const PLACEHOLDER = '/assets/images/placeholder-parfumer.jpg';

export default function ParfumerCard({ parfumer }) {
  const { id, name, nationality, imageUrl } = parfumer;
  return (
    <Link to={`/parfumer/${id}`} className="card card--portrait reveal">
      <div className="card__img-wrap">
        <img src={imageUrl ?? PLACEHOLDER} alt={name} loading="lazy" />
        <div className="card__img-overlay" />
      </div>
      <div className="card__body">
        <span className="label-eyebrow card__brand">{nationality}</span>
        <h3 className="card__name">{name}</h3>
      </div>
      <div className="card__accent" />
    </Link>
  );
}
