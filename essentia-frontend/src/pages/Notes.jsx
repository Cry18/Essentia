import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getNotes } from '../api/catalog.js';
import Pagination from '../components/Pagination.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './ListPage.css';
import './Notes.css';

export default function Notes() {
  const [data,    setData]    = useState(null);
  const [page,    setPage]    = useState(0);
  const [query,   setQuery]   = useState('');
  const [loading, setLoading] = useState(true);
  const ref = useScrollReveal([data]);

  useEffect(() => {
    setLoading(true);
    const params = { page, size: 30 };
    if (query) params.name = query;
    getNotes(params)
      .then(setData)
      .catch(() => setData({ content: [], totalPages: 0 }))
      .finally(() => setLoading(false));
  }, [page, query]);

  const handleSearch = (e) => {
    e.preventDefault();
    setQuery(new FormData(e.target).get('name'));
    setPage(0);
  };

  return (
    <div className="list-page" ref={ref}>
      <div className="list-page__hero">
        <div className="container">
          <span className="label-eyebrow anim-fade">Ingredienti &amp; materie prime</span>
          <h1 className="list-page__title anim-reveal" style={{ animationDelay: '200ms' }}>
            Note olfattive
          </h1>
        </div>
      </div>

      <div className="container">
        <form className="list-page__search reveal" onSubmit={handleSearch}>
          <div className="input-group">
            <label className="input-label">Cerca nota</label>
            <input name="name" className="input-field" placeholder="es. Bergamotto" defaultValue={query} />
          </div>
          <button type="submit" className="btn btn-ghost">Cerca</button>
        </form>

        {loading && <div className="spinner-wrap"><div className="spinner" /></div>}

        {!loading && (
          data?.content?.length > 0 ? (
            <>
              <div className="notes__grid">
                {data.content.map(note => (
                  <Link key={note.id} to={`/note/${note.id}`} className="note-card reveal">
                    {note.imageUrl && (
                      <div className="note-card__img-wrap">
                        <img src={note.imageUrl} alt={note.name} />
                      </div>
                    )}
                    <span className="note-card__name">{note.name}</span>
                  </Link>
                ))}
              </div>
              <Pagination page={page} totalPages={data.totalPages} onPageChange={setPage} />
            </>
          ) : (
            <p className="empty-state">Nessuna nota trovata.</p>
          )
        )}
      </div>
    </div>
  );
}
