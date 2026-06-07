import { Link } from 'react-router-dom';
import './Cards.css';

const PLACEHOLDER = '/assets/images/placeholder-brand.jpg';

export default function BrandCard({ brand }) {
  const { id, name, nationality, description, imageUrl } = brand;
  return (
    <Link to={`/brand/${id}`} className="card card--landscape reveal">
      <div className="card__img-wrap">
        <img src={imageUrl ?? PLACEHOLDER} alt={name} loading="lazy" />
        <div className="card__img-overlay" />
      </div>
      <div className="card__body">
        <span className="label-eyebrow card__brand">{nationality}</span>
        <h3 className="card__name">{name}</h3>
        {description && (
          <p className="card__excerpt">
            {description.length > 90 ? description.slice(0, 90) + '…' : description}
          </p>
        )}
      </div>
      <div className="card__accent" />
    </Link>
  );
}
