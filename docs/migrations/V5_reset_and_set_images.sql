-- ════════════════════════════════════════════════════════════════════
--  V5 — Reset image_url e riassegna placeholder da /images
--
--  1. Azzera tutte le immagini (brand, parfumer, perfume, users)
--  2. Riassegna il placeholder corretto per ogni tabella
--     (basato sui file presenti in images/)
--
--  Esegui su container in esecuzione:
--    docker exec -i essentia-mysql mysql -u essentia -pessentia_pass essentia \
--      < docs/migrations/V5_reset_and_set_images.sql
-- ════════════════════════════════════════════════════════════════════

-- 1. Azzera tutto
UPDATE brand    SET image_url = NULL;
UPDATE parfumer SET image_url = NULL;
UPDATE perfume  SET image_url = NULL;
UPDATE users    SET image_url = NULL;

-- 2. Riassegna placeholder per entità senza immagine dedicata
UPDATE brand    SET image_url = '/assets/images/placeholder-brand.jpg';
UPDATE parfumer SET image_url = '/assets/images/placeholder-parfumer.jpg';
UPDATE perfume  SET image_url = '/assets/images/placeholder-perfume.jpg';
-- users rimane NULL: nessun placeholder di profilo nella cartella images/
