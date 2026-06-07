-- V3: Aggiunta colonna image_url alle entità principali
-- Eseguire manualmente sul database MySQL prima del riavvio dei moduli

ALTER TABLE brand       ADD COLUMN image_url VARCHAR(500) NULL;
ALTER TABLE parfumer    ADD COLUMN image_url VARCHAR(500) NULL;
ALTER TABLE perfumenotes ADD COLUMN image_url VARCHAR(500) NULL;
ALTER TABLE perfume     ADD COLUMN image_url VARCHAR(500) NULL;
ALTER TABLE users       ADD COLUMN image_url VARCHAR(500) NULL;
