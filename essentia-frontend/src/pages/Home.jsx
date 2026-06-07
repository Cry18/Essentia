import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getPerfumes, getBrands } from '../api/catalog.js';
import PerfumeCard from '../components/PerfumeCard.jsx';
import BrandCard   from '../components/BrandCard.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './Home.css';

export default function Home() {
  const [perfumes, setPerfumes] = useState([]);
  const [brands,   setBrands]   = useState([]);
  const ref = useScrollReveal([perfumes, brands]);

  useEffect(() => {
    getPerfumes({ page: 0, size: 8 }).then(p => setPerfumes(p.content ?? []));
    getBrands  ({ page: 0, size: 6 }).then(b => setBrands  (b.content ?? []));
  }, []);

  return (
    <div className="home" ref={ref}>

      {/* ── Hero ────────────────────────────────────────────────────────── */}
      <section className="hero">
        <div className="hero__bg" />
        <div className="hero__overlay" />
        <div className="hero__content">
          <span className="label-eyebrow hero__eyebrow anim-fade" style={{ animationDelay: '200ms' }}>
            Catalogo di profumeria
          </span>
          <h1 className="hero__title anim-reveal" style={{ animationDelay: '400ms' }}>
            ESSENTIA
          </h1>
          <p className="hero__subtitle anim-slide-up" style={{ animationDelay: '700ms' }}>
            Esplora il mondo delle fragranze d'autore.<br />
            Scopri, colleziona, racconta.
          </p>
          <div className="hero__actions anim-slide-up" style={{ animationDelay: '900ms' }}>
            <Link to="/catalog" className="btn btn-ghost">Esplora il catalogo</Link>
            <Link to="/register" className="btn btn-text">Crea il tuo profilo →</Link>
          </div>
        </div>
        <div className="hero__scroll-hint">
          <span />
        </div>
      </section>

      {/* ── Featured perfumes ────────────────────────────────────────────── */}
      {perfumes.length > 0 && (
        <section className="section home__featured">
          <div className="container">
            <div className="section-header reveal">
              <span className="label-eyebrow">In evidenza</span>
              <h2 className="section-title">Fragranze scelte</h2>
            </div>
            <div className="home__grid">
              {perfumes.map(p => <PerfumeCard key={p.id} perfume={p} />)}
            </div>
            <div className="section-footer reveal">
              <Link to="/catalog" className="btn btn-ghost">Sfoglia tutto il catalogo</Link>
            </div>
          </div>
        </section>
      )}

      {/* ── Divider ─────────────────────────────────────────────────────── */}
      <div className="container">
        <div className="section-divider">
          <span /><span className="ornament">◆</span><span />
        </div>
      </div>

      {/* ── Brand ───────────────────────────────────────────────────────── */}
      {brands.length > 0 && (
        <section className="section home__brands">
          <div className="container">
            <div className="section-header reveal">
              <span className="label-eyebrow">Case produttrici</span>
              <h2 className="section-title">I Brand</h2>
            </div>
            <div className="home__brands-grid">
              {brands.map(b => <BrandCard key={b.id} brand={b} />)}
            </div>
            <div className="section-footer reveal">
              <Link to="/brands" className="btn btn-ghost">Tutti i brand</Link>
            </div>
          </div>
        </section>
      )}

      {/* ── Manifesto ───────────────────────────────────────────────────── */}
      <section className="section home__manifesto">
        <div className="container">
          <div className="manifesto reveal">
            <span className="label-eyebrow">Il nostro approccio</span>
            <blockquote className="manifesto__quote">
              "Una fragranza non è solo un profumo.<br />
              È una memoria, un'emozione, un'identità."
            </blockquote>
            <p className="manifesto__body">
              Essentia raccoglie le grandi firme della profumeria mondiale.
              Ogni scheda è una porta su un universo olfattivo —
              dalla costruzione delle note alle storie dei profumieri.
            </p>
          </div>
        </div>
      </section>

    </div>
  );
}
