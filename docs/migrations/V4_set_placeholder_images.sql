-- ════════════════════════════════════════════════════════════════════
--  V4 — Imposta immagini placeholder per le entità senza imageUrl
--
--  Esegui su container in esecuzione:
--    docker exec -i essentia-mysql mysql -u essentia -pessentia_pass essentia \
--      < docs/migrations/V4_set_placeholder_images.sql
--
--  Impatto: aggiorna SOLO le righe con image_url NULL (non sovrascrive
--  immagini già caricate tramite il pannello admin).
-- ════════════════════════════════════════════════════════════════════

UPDATE brand
   SET image_url = '/assets/images/placeholder-brand.jpg'
 WHERE image_url IS NULL;

UPDATE parfumer
   SET image_url = '/assets/images/placeholder-parfumer.jpg'
 WHERE image_url IS NULL;

UPDATE perfume
   SET image_url = '/assets/images/placeholder-perfume.jpg'
 WHERE image_url IS NULL;

-- Note olfattive: nessuna immagine dedicata per ora, lasciamo NULL
-- UPDATE perfumenotes SET image_url = '/assets/images/placeholder-perfume.jpg' WHERE image_url IS NULL;
