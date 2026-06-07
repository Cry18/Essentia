import { useEffect, useState } from 'react';
import { getBrands } from '../api/catalog.js';
import BrandCard  from '../components/BrandCard.jsx';
import Pagination from '../components/Pagination.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './ListPage.css';

export default function Brands() {
  const [data,    setData]    = useState(null);
  const [page,    setPage]    = useState(0);
  const [query,   setQuery]   = useState('');
  const [loading, setLoading] = useState(true);
  const ref = useScrollReveal([data]);

  useEffect(() => {
    setLoading(true);
    const params = { page, size: 20 };
    if (query) params.name = query;
    getBrands(params)
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
          <span className="label-eyebrow anim-fade">Case produttrici</span>
          <h1 className="list-page__title anim-reveal" style={{ animationDelay: '200ms' }}>Brand</h1>
        </div>
      </div>

      <div className="container">
        <form className="list-page__search reveal" onSubmit={handleSearch}>
          <div className="input-group">
            <label className="input-label">Cerca brand</label>
            <input name="name" className="input-field" placeholder="es. Chanel" defaultValue={query} />
          </div>
          <button type="submit" className="btn btn-ghost">Cerca</button>
        </form>

        {loading && <div className="spinner-wrap"><div className="spinner" /></div>}

        {!loading && (
          data?.content?.length > 0 ? (
            <>
              <div className="list-page__grid">
                {data.content.map(b => <BrandCard key={b.id} brand={b} />)}
              </div>
              <Pagination page={page} totalPages={data.totalPages} onPageChange={setPage} />
            </>
          ) : (
            <p className="empty-state">Nessun brand trovato.</p>
          )
        )}
      </div>
    </div>
  );
}
