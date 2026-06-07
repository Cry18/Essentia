import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getBrand, getPerfumes } from '../api/catalog.js';
import PerfumeCard from '../components/PerfumeCard.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './DetailPage.css';

const PLACEHOLDER = '/assets/images/placeholder-brand.jpg';

export default function BrandDetail() {
  const { id } = useParams();
  const [brand,    setBrand]    = useState(null);
  const [perfumes, setPerfumes] = useState([]);
  const [loading,  setLoading]  = useState(true);
  const ref = useScrollReveal([brand, perfumes]);

  useEffect(() => {
    setLoading(true);
    Promise.all([
      getBrand(id),
      getPerfumes({ brand: '', page: 0, size: 20 }), // filtro per brand non disponibile direttamente
    ])
      .then(([b]) => { setBrand(b); })
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="spinner-wrap"><div className="spinner" /></div>;
  if (!brand)  return <p className="empty-state">Brand non trovato.</p>;

  return (
    <div className="detail-page" ref={ref}>
      <div className="detail-page__header">
        <div className="container detail-page__header-inner">
          <div className="detail-page__header-text">
            <span className="label-eyebrow anim-fade">{brand.nationality}</span>
            <h1 className="detail-page__title anim-reveal" style={{ animationDelay: '150ms' }}>
              {brand.name}
            </h1>
          </div>
          {brand.imageUrl && (
            <div className="detail-page__img-wrap anim-fade" style={{ animationDelay: '200ms' }}>
              <img src={brand.imageUrl ?? PLACEHOLDER} alt={brand.name} className="detail-page__img" />
            </div>
          )}
        </div>
      </div>
      <div className="container detail-page__body">
        <p className="detail-page__description reveal">{brand.description}</p>
        <div className="section-divider"><span /><span className="ornament">◆</span><span /></div>
        <p className="empty-state" style={{ padding: 'var(--space-6) 0' }}>
          Esplora le fragranze di questo brand nel{' '}
          <Link to={`/catalog?brand=${encodeURIComponent(brand.name)}`} className="btn-text">
            catalogo →
          </Link>
        </p>
      </div>
    </div>
  );
}
