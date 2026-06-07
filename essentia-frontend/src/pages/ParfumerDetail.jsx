import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getParfumer } from '../api/catalog.js';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './DetailPage.css';

const PLACEHOLDER = '/assets/images/placeholder-parfumer.jpg';

export default function ParfumerDetail() {
  const { id } = useParams();
  const [parfumer, setParfumer] = useState(null);
  const [loading,  setLoading]  = useState(true);
  const ref = useScrollReveal([parfumer]);

  useEffect(() => {
    getParfumer(id).then(setParfumer).catch(() => {}).finally(() => setLoading(false));
  }, [id]);

  if (loading)   return <div className="spinner-wrap"><div className="spinner" /></div>;
  if (!parfumer) return <p className="empty-state">Profumiere non trovato.</p>;

  return (
    <div className="detail-page" ref={ref}>
      <div className="detail-page__header">
        <div className="container detail-page__header-inner">
          <div className="detail-page__header-text">
            <span className="label-eyebrow anim-fade">{parfumer.nationality}</span>
            <h1 className="detail-page__title anim-reveal" style={{ animationDelay: '150ms' }}>
              {parfumer.name}
            </h1>
          </div>
          {parfumer.imageUrl && (
            <div className="detail-page__img-wrap anim-fade" style={{ animationDelay: '200ms' }}>
              <img src={parfumer.imageUrl ?? PLACEHOLDER} alt={parfumer.name} className="detail-page__img" />
            </div>
          )}
        </div>
      </div>
      <div className="container detail-page__body">
        <p className="detail-page__description reveal">{parfumer.description}</p>
        <div style={{ padding: 'var(--space-6) 0' }}>
          <Link
            to={`/catalog?parfumer=${encodeURIComponent(parfumer.name)}`}
            className="btn btn-ghost"
          >
            Esplora le creazioni di {parfumer.name} →
          </Link>
        </div>
      </div>
    </div>
  );
}
