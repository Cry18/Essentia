-- ============================================================
-- Migrazione V1 — Aggiunta campi autenticazione alla tabella user
-- Da eseguire una sola volta prima del primo avvio con Spring Security
-- ============================================================

-- CASO A: tabella user vuota (nessun dato esistente)
ALTER TABLE `user`
    ADD COLUMN `username` VARCHAR(50) NOT NULL UNIQUE AFTER `surname`,
    ADD COLUMN `password` VARCHAR(255) NOT NULL AFTER `username`;

-- ============================================================
-- CASO B: tabella user con dati esistenti
-- Eseguire in questo ordine:

-- 1. Aggiungi le colonne come nullable
-- ALTER TABLE `user`
--     ADD COLUMN `username` VARCHAR(50) NULL AFTER `surname`,
--     ADD COLUMN `password` VARCHAR(255) NULL AFTER `username`;

-- 2. Assegna username e password temporanee agli utenti esistenti
--    (la password verrà sovrascritta al primo login)
-- UPDATE `user` SET username = CONCAT('user_', id), password = 'CHANGEME' WHERE username IS NULL;

-- 3. Aggiungi i vincoli NOT NULL e UNIQUE
-- ALTER TABLE `user`
--     MODIFY COLUMN `username` VARCHAR(50) NOT NULL,
--     ADD UNIQUE INDEX `uk_user_username` (`username`),
--     MODIFY COLUMN `password` VARCHAR(255) NOT NULL;

-- ============================================================
-- NOTA: gli utenti con password 'CHANGEME' dovranno essere
-- ricreati via /api/auth/register oppure aggiornati direttamente
-- con un hash BCrypt valido.
-- ============================================================
