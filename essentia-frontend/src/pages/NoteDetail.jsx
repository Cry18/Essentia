import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getNote, getPerfumes } from '../api/catalog.js';
import PerfumeCard from '../components/PerfumeCard.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './DetailPage.css';

export default function NoteDetail() {
  const { id } = useParams();
  const [note,     setNote]     = useState(null);
  const [perfumes, setPerfumes] = useState([]);
  const [loading,  setLoading]  = useState(true);
  const ref = useScrollReveal([note, perfumes]);

  useEffect(() => {
    setLoading(true);
    getNote(id)
      .then(async (n) => {
        setNote(n);
        try {
          const result = await getPerfumes({ note: n.name, size: 50 });
          setPerfumes(result?.content ?? []);
        } catch {
          setPerfumes([]);
        }
      })
      .catch(() => setNote(null))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="spinner-wrap"><div className="spinner" /></div>;
  if (!note)   return <p className="empty-state">Nota non trovata.</p>;

  return (
    <div className="detail-page" ref={ref}>

      <div className="detail-page__header">
        <div className="container detail-page__header-inner">
          <div className="detail-page__header-text">
            <Link to="/notes" className="label-eyebrow anim-fade" style={{ textDecoration: 'none' }}>
              ← Note olfattive
            </Link>
            <h1 className="detail-page__title anim-reveal" style={{ animationDelay: '150ms' }}>
              {note.name}
            </h1>
          </div>
          {note.imageUrl && (
            <div className="detail-page__img-wrap anim-fade" style={{ animationDelay: '200ms' }}>
              <img src={note.imageUrl} alt={note.name} className="detail-page__img" />
            </div>
          )}
        </div>
      </div>

      <div className="container detail-page__body">

        {note.description && (
          <section className="detail-page__section reveal">
            <p className="detail-page__description">{note.description}</p>
          </section>
        )}

        <div className="section-divider">
          <span /><span className="ornament">◆</span><span />
        </div>

        <section className="detail-page__section reveal">
          <h2 className="detail-page__section-title">
            Fragranze con {note.name}
            {perfumes.length > 0 && ` (${perfumes.length})`}
          </h2>
          {perfumes.length > 0 ? (
            <div className="list-page__grid">
              {perfumes.map(p => <PerfumeCard key={p.id} perfume={p} />)}
            </div>
          ) : (
            <p className="empty-state">
              Nessuna fragranza nel catalogo utilizza questa nota.
            </p>
          )}
        </section>

      </div>
    </div>
  );
}
