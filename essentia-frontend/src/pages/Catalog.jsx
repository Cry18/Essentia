import { useEffect, useState, useCallback } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getPerfumes } from '../api/catalog.js';
import PerfumeCard  from '../components/PerfumeCard.jsx';
import Pagination   from '../components/Pagination.jsx';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './Catalog.css';

const PAGE_SIZE = 20;

export default function Catalog() {
  const [searchParams, setSearchParams] = useSearchParams();

  const [filters, setFilters] = useState({
    name:     searchParams.get('name')     ?? '',
    brand:    searchParams.get('brand')    ?? '',
    parfumer: searchParams.get('parfumer') ?? '',
    note:     searchParams.get('note')     ?? '',
  });
  const [page,       setPage]       = useState(0);
  const [formKey,    setFormKey]    = useState(0);
  const [data,       setData]       = useState(null);
  const [loading,    setLoading]    = useState(true);
  const [error,      setError]      = useState(null);

  const ref = useScrollReveal([data]);

  const fetchPerfumes = useCallback(async (f, p) => {
    setLoading(true); setError(null);
    try {
      const params = { page: p, size: PAGE_SIZE };
      if (f.name)     params.name     = f.name;
      if (f.brand)    params.brand    = f.brand;
      if (f.parfumer) params.parfumer = f.parfumer;
      if (f.note)     params.note     = f.note;
      const result = await getPerfumes(params);
      setData(result);
    } catch (e) {
      if (e.response?.status === 404) setData({ content: [], totalPages: 0 });
      else setError('Impossibile caricare i profumi. Riprova.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchPerfumes(filters, page);
  }, [fetchPerfumes, filters, page]);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    const fd = new FormData(e.target);
    const f = {
      name:     fd.get('name')     ?? '',
      brand:    fd.get('brand')    ?? '',
      parfumer: fd.get('parfumer') ?? '',
      note:     fd.get('note')     ?? '',
    };
    setFilters(f);
    // Aggiorna URL params
    const sp = new URLSearchParams();
    Object.entries(f).forEach(([k, v]) => { if (v) sp.set(k, v); });
    setSearchParams(sp);
  };

  const handleReset = () => {
    setFilters({ name: '', brand: '', parfumer: '', note: '' });
    setPage(0);
    setFormKey(k => k + 1); // forza rimount del form azzerando i valori DOM
    setSearchParams({});
  };

  return (
    <div className="catalog" ref={ref}>
      {/* Banner */}
      <div className="catalog__hero">
        <div className="catalog__hero-bg" />
        <div className="catalog__hero-overlay" />
        <div className="catalog__hero-content">
          <span className="label-eyebrow anim-fade">Esplora</span>
          <h1 className="catalog__hero-title anim-reveal" style={{ animationDelay: '200ms' }}>
            Catalogo
          </h1>
        </div>
      </div>

      <div className="container">
        {/* Filtri */}
        <form key={formKey} className="catalog__filters reveal" onSubmit={handleSearch}>
          <div className="catalog__filter-grid">
            {[
              { name: 'name',     label: 'Nome profumo',  placeholder: 'es. Sauvage' },
              { name: 'brand',    label: 'Brand',          placeholder: 'es. Dior' },
              { name: 'parfumer', label: 'Profumiere',     placeholder: 'es. Alberto Morillas' },
              { name: 'note',     label: 'Nota olfattiva', placeholder: 'es. Bergamotto' },
            ].map(f => (
              <div key={f.name} className="input-group">
                <label className="input-label" htmlFor={f.name}>{f.label}</label>
                <input
                  id={f.name} name={f.name}
                  className="input-field"
                  defaultValue={filters[f.name]}
                  placeholder={f.placeholder}
                />
              </div>
            ))}
          </div>
          <div className="catalog__filter-actions">
            <button type="submit" className="btn btn-ghost">Cerca</button>
            <button type="button" className="btn btn-text" onClick={handleReset}>Azzera filtri</button>
          </div>
        </form>

        {/* Risultati */}
        {loading && (
          <div className="spinner-wrap"><div className="spinner" /></div>
        )}
        {error && <p className="catalog__error">{error}</p>}

        {!loading && !error && (
          <>
            {data?.content?.length > 0 ? (
              <>
                <p className="catalog__count reveal">
                  {data.totalElements} {data.totalElements === 1 ? 'fragranza trovata' : 'fragranze trovate'}
                </p>
                <div className="catalog__grid">
                  {data.content.map(p => <PerfumeCard key={p.id} perfume={p} />)}
                </div>
                <Pagination
                  page={page}
                  totalPages={data.totalPages}
                  onPageChange={p => { setPage(p); window.scrollTo({ top: 0, behavior: 'smooth' }); }}
                />
              </>
            ) : (
              <p className="empty-state">
                Nessuna fragranza corrisponde ai filtri selezionati.
              </p>
            )}
          </>
        )}
      </div>
    </div>
  );
}
