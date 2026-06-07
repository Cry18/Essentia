import { useEffect, useRef } from 'react';

/**
 * Aggiunge la classe .visible agli elementi .reveal
 * quando entrano nel viewport — attiva l'animazione slide-up.
 */
export default function useScrollReveal(deps = []) {
  const ref = useRef(null);

  useEffect(() => {
    const root = ref.current ?? document;
    const elements = root.querySelectorAll('.reveal');
    if (!elements.length) return;

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry, i) => {
          if (entry.isIntersecting) {
            // Stagger per elementi multipli
            setTimeout(() => entry.target.classList.add('visible'), i * 80);
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.08 }
    );

    elements.forEach(el => observer.observe(el));
    return () => observer.disconnect();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  return ref;
}
