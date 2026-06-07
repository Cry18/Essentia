import { Component } from 'react';

export default class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, message: '' };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, message: error?.message ?? 'Errore sconosciuto' };
  }

  componentDidCatch(error, info) {
    console.error('[Essentia] Render error:', error, info);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div style={{
          minHeight: '60vh',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          justifyContent: 'center',
          gap: '1.5rem',
          padding: '4rem 2rem',
          textAlign: 'center',
          fontFamily: 'Raleway, sans-serif',
          color: '#a89f8c',
        }}>
          <span style={{ fontFamily: 'Cormorant Garamond, serif', fontSize: '4rem', color: '#c9a84c', letterSpacing: '0.1em' }}>
            Errore
          </span>
          <p style={{ fontSize: '1rem', maxWidth: '480px', lineHeight: 1.8 }}>
            Si è verificato un problema nel caricamento di questa sezione.
          </p>
          <p style={{ fontSize: '0.75rem', color: '#5c5548', fontFamily: 'monospace' }}>
            {this.state.message}
          </p>
          <button
            onClick={() => window.location.reload()}
            style={{
              border: '1px solid #c9a84c',
              color: '#c9a84c',
              background: 'transparent',
              padding: '10px 28px',
              fontFamily: 'Raleway, sans-serif',
              fontSize: '0.75rem',
              letterSpacing: '0.12em',
              textTransform: 'uppercase',
              cursor: 'pointer',
            }}
          >
            Ricarica la pagina
          </button>
        </div>
      );
    }
    return this.props.children;
  }
}
