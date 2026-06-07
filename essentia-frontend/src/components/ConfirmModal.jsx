import { useEffect } from 'react';
import './ConfirmModal.css';

/**
 * Modal di conferma generico.
 *
 * Props:
 *   message  – testo da mostrare (es. "Eliminare questa recensione?")
 *   onConfirm – callback se l'utente conferma
 *   onCancel  – callback se l'utente annulla
 */
export default function ConfirmModal({ message, onConfirm, onCancel }) {
  /* Chiudi con ESC */
  useEffect(() => {
    const onKey = (e) => { if (e.key === 'Escape') onCancel(); };
    window.addEventListener('keydown', onKey);
    return () => window.removeEventListener('keydown', onKey);
  }, [onCancel]);

  return (
    <div className="confirm-overlay" onClick={onCancel}>
      <div className="confirm-dialog" onClick={e => e.stopPropagation()}>
        <p className="confirm-dialog__message">{message}</p>
        <div className="confirm-dialog__actions">
          <button className="btn btn-filled confirm-dialog__confirm" onClick={onConfirm}>
            Conferma
          </button>
          <button className="btn btn-ghost" onClick={onCancel}>
            Annulla
          </button>
        </div>
      </div>
    </div>
  );
}
