import { useState } from 'react';
import ConfirmModal from '../components/ConfirmModal.jsx';
import { createBrand, updateBrand, deleteBrand,
         createParfumer, updateParfumer, deleteParfumer,
         createNote, updateNote, deleteNote,
         createPerfume, updatePerfume, deletePerfume,
         uploadImage } from '../api/admin.js';
import { getBrands, getBrand, getParfumers, getParfumer, getNotes, getNote, getPerfumes, getPerfume } from '../api/catalog.js';
import useScrollReveal from '../hooks/useScrollReveal.js';
import './Admin.css';

const TABS = ['Brand', 'Profumieri', 'Note olfattive', 'Profumi'];

/* Nomi singolari corretti per ogni tab */
const TAB_SINGULAR = {
  'Brand':          'Brand',
  'Profumieri':     'Profumiere',
  'Note olfattive': 'Nota olfattiva',
  'Profumi':        'Profumo',
};

const NOTE_TYPES = [
  { value: 1, label: 'Testa' },
  { value: 2, label: 'Cuore' },
  { value: 3, label: 'Fondo' },
];

export default function Admin() {
  /* ── Stato principale ── */
  const [activeTab,         setActiveTab]         = useState('Brand');
  const [items,             setItems]             = useState([]);
  const [loaded,            setLoaded]            = useState(false);
  const [msg,               setMsg]               = useState('');
  const [formOpen,          setFormOpen]          = useState(false);
  const [editItem,          setEditItem]          = useState(null);

  /* ── Upload immagine ── */
  const [uploadedImageUrl,  setUploadedImageUrl]  = useState(null);
  const [uploading,         setUploading]         = useState(false);

  /* ── Dati per form Profumi ── */
  const [allBrands,         setAllBrands]         = useState([]);
  const [allParfumers,      setAllParfumers]      = useState([]);
  const [allNotes,          setAllNotes]          = useState([]);
  const [perfumeNotes,      setPerfumeNotes]      = useState([]);   // [{noteId, type}]
  const [selectedParfumers, setSelectedParfumers] = useState([]);   // [id, ...]
  const [selectedBrandId,   setSelectedBrandId]   = useState('');

  /* ── Delete confirm modal ── */
  const [deleteTarget,      setDeleteTarget]      = useState(null); // null | { id, name }
  const [msgType,           setMsgType]           = useState('success'); // 'success' | 'error'

  /* ── Loading edit profumo ── */
  const [editLoading,       setEditLoading]       = useState(false);

  const ref = useScrollReveal([items]);

  const flash = (text, type = 'success') => {
    setMsg(text);
    setMsgType(type);
    setTimeout(() => setMsg(''), 5000);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const flashError = (err) => {
    const resp = err?.response?.data;
    if (resp?.violations?.length) {
      const detail = resp.violations.map(v => `${v.field}: ${v.message}`).join(' — ');
      flash(`Errore: ${detail}`, 'error');
    } else if (resp?.message) {
      flash(resp.message, 'error');
    } else {
      flash('Operazione non riuscita. Controlla i dati e riprova.', 'error');
    }
  };

  const showForm = formOpen || editItem !== null;

  /* ── Reset completo ── */
  const resetForm = () => {
    setFormOpen(false);
    setEditItem(null);
    setUploadedImageUrl(null);
    setPerfumeNotes([]);
    setSelectedParfumers([]);
    setSelectedBrandId('');
    setDeleteTarget(null);
  };

  /* ── Carica elenco per tab ── */
  const loadItems = async (tab) => {
    setLoaded(false);
    setItems([]);
    resetForm();
    try {
      let data;
      if (tab === 'Brand')          data = await getBrands   ({ size: 100 });
      if (tab === 'Profumieri')     data = await getParfumers({ size: 100 });
      if (tab === 'Note olfattive') data = await getNotes    ({ size: 100 });
      if (tab === 'Profumi') {
        const [perfumesData, brandsData, parfumersData, notesData] = await Promise.all([
          getPerfumes ({ size: 100 }),
          getBrands   ({ size: 100 }),
          getParfumers({ size: 100 }),
          getNotes    ({ size: 100 }),
        ]);
        data = perfumesData;
        setAllBrands   (brandsData?.content    ?? []);
        setAllParfumers(parfumersData?.content ?? []);
        setAllNotes    (notesData?.content     ?? []);
        setSelectedBrandId(brandsData?.content?.[0]?.id?.toString() ?? '');
      }
      setItems(data?.content ?? []);
    } catch (err) {
      flashError(err);
    }
    setLoaded(true);
  };

  const handleTabChange = (tab) => { setActiveTab(tab); loadItems(tab); };

  /* ── Apri / chiudi form nuovo elemento ── */
  const handleNewItem = () => {
    if (showForm) {
      resetForm();
      loadItems(activeTab);
    } else {
      resetForm();
      setSelectedBrandId(allBrands[0]?.id?.toString() ?? '');
      setFormOpen(true);
    }
  };

  /* ── Upload immagine ── */
  const handleImageSelect = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setUploading(true);
    try {
      const url = await uploadImage(file);
      setUploadedImageUrl(url);
    } catch (err) {
      flash('Caricamento immagine fallito.', 'error');
    } finally {
      setUploading(false);
    }
  };

  /* ── Submit form ── */
  const handleSubmit = async (e) => {
    e.preventDefault();
    const fd = new FormData(e.target);
    const imageUrl = uploadedImageUrl ?? editItem?.imageUrl ?? null;

    try {
      if (activeTab === 'Brand') {
        const payload = {
          name:        fd.get('name'),
          description: fd.get('description'),
          nationality: fd.get('nationality'),
          imageUrl,
        };
        editItem ? await updateBrand(editItem.id, payload) : await createBrand(payload);

      } else if (activeTab === 'Profumieri') {
        const payload = {
          name:        fd.get('name'),
          description: fd.get('description'),
          nationality: fd.get('nationality'),
          imageUrl,
        };
        editItem ? await updateParfumer(editItem.id, payload) : await createParfumer(payload);

      } else if (activeTab === 'Note olfattive') {
        const payload = {
          name:        fd.get('name'),
          description: fd.get('description') ?? '',
        };
        editItem ? await updateNote(editItem.id, payload) : await createNote(payload);

      } else if (activeTab === 'Profumi') {
        if (!selectedBrandId)            { flash('Seleziona un brand.');             return; }
        if (selectedParfumers.length === 0) { flash('Seleziona almeno un profumiere.'); return; }
        if (perfumeNotes.length === 0)   { flash('Aggiungi almeno una nota olfattiva.'); return; }
        const payload = {
          name:        fd.get('name'),
          description: fd.get('description'),
          brand:       parseInt(selectedBrandId),
          parfumers:   selectedParfumers,
          notes:       perfumeNotes,
          imageUrl,
        };
        editItem ? await updatePerfume(editItem.id, payload) : await createPerfume(payload);
      }

      flash(editItem ? 'Aggiornato con successo.' : 'Creato con successo.', 'success');
      resetForm();
      loadItems(activeTab);
    } catch (err) {
      flashError(err);
    }
  };

  /* ── Delete con conferma modal ── */
  const handleDelete = async () => {
    if (!deleteTarget) return;
    const { id } = deleteTarget;
    setDeleteTarget(null);
    try {
      if (activeTab === 'Brand')             await deleteBrand(id);
      else if (activeTab === 'Profumieri')   await deleteParfumer(id);
      else if (activeTab === 'Note olfattive') await deleteNote(id);
      else                                   await deletePerfume(id);
      flash('Eliminato con successo.', 'success');
      loadItems(activeTab);
    } catch (err) {
      flashError(err);
    }
  };

  /* ── Edit — carica sempre il dettaglio per avere tutti i campi ── */
  const handleEdit = async (item) => {
    setUploadedImageUrl(null);
    setFormOpen(false);
    setDeleteTarget(null);
    setEditLoading(true);
    try {
      let detail;
      if (activeTab === 'Brand') {
        detail = await getBrand(item.id);

      } else if (activeTab === 'Profumieri') {
        detail = await getParfumer(item.id);

      } else if (activeTab === 'Note olfattive') {
        detail = await getNote(item.id);

      } else if (activeTab === 'Profumi') {
        detail = await getPerfume(item.id);
        /* Brand: match per nome → id */
        const brand = allBrands.find(b => b.name === detail.brand);
        setSelectedBrandId(brand?.id?.toString() ?? allBrands[0]?.id?.toString() ?? '');
        /* Profumieri: match per nome → id */
        const parfIds = (detail.parfumers ?? [])
          .map(name => allParfumers.find(p => p.name === name)?.id)
          .filter(id => id !== undefined);
        setSelectedParfumers(parfIds);
        /* Note: match per nome → { noteId, type } */
        const noteRows = (detail.notes ?? [])
          .map(n => {
            const note = allNotes.find(an => an.name === n.name);
            return note ? { noteId: note.id, type: n.type } : null;
          })
          .filter(Boolean);
        setPerfumeNotes(noteRows);
      }
      setEditItem({ ...detail, id: item.id });
    } catch (err) {
      flashError(err);
    } finally {
      setEditLoading(false);
    }
  };

  /* ── Note builder ── */
  const addNoteRow = () => {
    if (allNotes.length === 0) return;
    setPerfumeNotes(n => [...n, { noteId: allNotes[0].id, type: 1 }]);
  };
  const updateNoteRow = (i, field, val) => {
    setPerfumeNotes(rows =>
      rows.map((row, idx) => idx === i ? { ...row, [field]: parseInt(val) } : row)
    );
  };
  const removeNoteRow = (i) => {
    setPerfumeNotes(rows => rows.filter((_, idx) => idx !== i));
  };

  /* ── Profumieri checkbox ── */
  const toggleParfumer = (id) => {
    setSelectedParfumers(prev =>
      prev.includes(id) ? prev.filter(p => p !== id) : [...prev, id]
    );
  };

  /* ─────────── RENDER ─────────── */
  const isPerfumi    = activeTab === 'Profumi';
  const isNoteOlfatt = activeTab === 'Note olfattive';

  return (
    <div className="admin" ref={ref}>
      <div className="admin__header">
        <div className="container">
          <span className="label-eyebrow anim-fade">Pannello di controllo</span>
          <h1 className="admin__title anim-reveal" style={{ animationDelay: '150ms' }}>
            Amministrazione
          </h1>
        </div>
      </div>

      <div className="container admin__body">

        {/* Tab bar */}
        <div className="admin__tabs reveal">
          {TABS.map(tab => (
            <button
              key={tab}
              className={`admin__tab ${activeTab === tab ? 'active' : ''}`}
              onClick={() => handleTabChange(tab)}
            >
              {tab}
            </button>
          ))}
        </div>

        {msg && <p className={`admin__msg admin__msg--${msgType}`}>{msg}</p>}

        {/* Toolbar */}
        <div className="admin__toolbar reveal">
          <button className="btn btn-ghost" onClick={handleNewItem}>
            {showForm ? 'Chiudi form' : `+ Nuovo ${TAB_SINGULAR[activeTab]}`}
          </button>
          {!loaded && !editLoading && (
            <button className="btn btn-ghost" onClick={() => loadItems(activeTab)}>
              Carica elenco
            </button>
          )}
        </div>

        {/* ══ FORM ══ */}
        {showForm && (
          <form
            key={editItem?.id ?? 'new-form'}
            className="admin__form anim-slide-up"
            onSubmit={handleSubmit}
          >
            <h3 className="admin__form-title">
              {editItem ? 'Modifica' : 'Nuovo'} {TAB_SINGULAR[activeTab]}
            </h3>

            <div className="admin__form-grid">

              {/* Nome */}
              <div className="input-group">
                <label className="input-label">Nome *</label>
                <input name="name" className="input-field" required
                  defaultValue={editItem?.name ?? ''} maxLength={30} />
              </div>

              {/* Nazionalità (Brand / Profumieri) */}
              {!isNoteOlfatt && !isPerfumi && (
                <div className="input-group">
                  <label className="input-label">Nazionalità *</label>
                  <input name="nationality" className="input-field" required
                    defaultValue={editItem?.nationality ?? ''} maxLength={30} />
                </div>
              )}

              {/* Brand select (Profumi) */}
              {isPerfumi && (
                <div className="input-group">
                  <label className="input-label">Brand *</label>
                  <select name="brand" className="input-field" required
                    value={selectedBrandId}
                    onChange={e => setSelectedBrandId(e.target.value)}
                  >
                    <option value="">Seleziona brand…</option>
                    {allBrands.map(b => (
                      <option key={b.id} value={b.id}>{b.name}</option>
                    ))}
                  </select>
                </div>
              )}

              {/* Descrizione */}
              <div className="input-group" style={{ gridColumn: '1/-1' }}>
                <label className="input-label">Descrizione *</label>
                <textarea name="description" className="input-field admin__textarea" required
                  defaultValue={editItem?.description ?? ''} minLength={10} />
              </div>

            </div>

            {/* ── Profumieri checkboxes (solo Profumi) ── */}
            {isPerfumi && (
              <div className="admin__form-section">
                <p className="admin__form-section-title">Profumieri *</p>
                <div className="admin__checkboxes">
                  {allParfumers.map(p => (
                    <label key={p.id} className="admin__checkbox-label">
                      <input
                        type="checkbox"
                        checked={selectedParfumers.includes(p.id)}
                        onChange={() => toggleParfumer(p.id)}
                      />
                      {p.name}
                    </label>
                  ))}
                </div>
              </div>
            )}

            {/* ── Note olfattive builder (solo Profumi) ── */}
            {isPerfumi && (
              <div className="admin__form-section">
                <div className="admin__form-section-header">
                  <p className="admin__form-section-title">Note olfattive *</p>
                  <button type="button" className="btn btn-text" onClick={addNoteRow}>
                    + Aggiungi nota
                  </button>
                </div>
                {perfumeNotes.length === 0 ? (
                  <p className="admin__notes-empty">
                    Nessuna nota aggiunta. Clicca "+ Aggiungi nota".
                  </p>
                ) : (
                  <div className="admin__notes-list">
                    {perfumeNotes.map((row, i) => (
                      <div key={i} className="admin__note-row">
                        <select
                          className="input-field admin__note-col"
                          value={row.noteId}
                          onChange={e => updateNoteRow(i, 'noteId', e.target.value)}
                        >
                          {allNotes.map(n => (
                            <option key={n.id} value={n.id}>{n.name}</option>
                          ))}
                        </select>
                        <select
                          className="input-field admin__note-col"
                          value={row.type}
                          onChange={e => updateNoteRow(i, 'type', e.target.value)}
                        >
                          {NOTE_TYPES.map(t => (
                            <option key={t.value} value={t.value}>{t.label}</option>
                          ))}
                        </select>
                        <button
                          type="button"
                          className="admin__note-remove"
                          onClick={() => removeNoteRow(i)}
                          title="Rimuovi nota"
                        >
                          ×
                        </button>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            )}

            {/* ── Upload immagine (non per Note olfattive) ── */}
            {!isNoteOlfatt && (
              <div className="admin__form-section">
                <p className="admin__form-section-title">
                  {editItem?.imageUrl && !uploadedImageUrl
                    ? 'Foto attuale — carica per sostituire'
                    : 'Foto'}
                </p>
                <div className="admin__upload-row">
                  <label className={`btn btn-ghost admin__upload-label ${uploading ? 'disabled' : ''}`}>
                    {uploading ? 'Caricamento…' : 'Scegli immagine'}
                    <input
                      type="file"
                      accept="image/jpeg,image/png,image/webp"
                      onChange={handleImageSelect}
                      hidden
                      disabled={uploading}
                    />
                  </label>
                  {(uploadedImageUrl ?? editItem?.imageUrl) && (
                    <div className="admin__img-preview">
                      <img src={uploadedImageUrl ?? editItem.imageUrl} alt="preview" />
                      {uploadedImageUrl && (
                        <span className="admin__img-preview-ok">✓ Caricata</span>
                      )}
                    </div>
                  )}
                </div>
              </div>
            )}

            <div className="admin__form-actions">
              <button type="submit" className="btn btn-filled">
                {editItem ? 'Salva modifiche' : 'Crea'}
              </button>
              <button type="button" className="btn btn-text" onClick={() => { resetForm(); loadItems(activeTab); }}>
                Annulla
              </button>
            </div>
          </form>
        )}

        {/* Spinner modifica profumo */}
        {editLoading && (
          <div className="spinner-wrap"><div className="spinner" /></div>
        )}

        {/* ══ LISTA ══ */}
        {loaded && !editLoading && (
          <div className="admin__list">
            {items.length === 0 ? (
              <p className="empty-state">Nessun elemento presente.</p>
            ) : items.map(item => (
              <div key={item.id} className="admin__item reveal">

                {item.imageUrl && (
                  <div className="admin__item-thumb">
                    <img src={item.imageUrl} alt={item.name} />
                  </div>
                )}

                <div className="admin__item-info">
                  <span className="admin__item-name">{item.name}</span>
                  {item.nationality && (
                    <span className="label-eyebrow">{item.nationality}</span>
                  )}
                  {!item.nationality && item.brand && (
                    <span className="label-eyebrow">{item.brand}</span>
                  )}
                </div>

                <div className="admin__item-actions">
                  <button className="btn btn-text" onClick={() => handleEdit(item)}>
                    Modifica
                  </button>
                  <button
                    className="btn btn-text admin__item-delete"
                    onClick={() => setDeleteTarget({ id: item.id, name: item.name })}
                  >
                    Elimina
                  </button>
                </div>

              </div>
            ))}
          </div>
        )}

      </div>

      {deleteTarget && (
        <ConfirmModal
          message={`Eliminare ${TAB_SINGULAR[activeTab].toLowerCase()} "${deleteTarget.name}"? L'operazione non è reversibile.`}
          onConfirm={handleDelete}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  );
}
