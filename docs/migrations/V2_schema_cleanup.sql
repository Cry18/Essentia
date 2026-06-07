-- ══════════════════════════════════════════════════════════════════════════════
--  V2 — Schema cleanup
--
--  Run this script BEFORE restarting the Spring applications.
--
--  Docker (fresh start — drops all data):
--    docker compose down -v
--    docker compose up --build
--    (DataSeeder will re-populate the DB automatically)
--
--  Docker (keep existing data — run manually):
--    docker exec -i essentia-mysql mysql -u essentia -pessentia_pass essentia < docs/migrations/V2_schema_cleanup.sql
-- ══════════════════════════════════════════════════════════════════════════════

USE essentia;

-- #18: Rename reserved MySQL keyword 'user' table to 'users'
RENAME TABLE `user` TO `users`;

-- #18: Rename FK column 'user' → 'user_id' in tables that reference the user table
ALTER TABLE `shelf`     RENAME COLUMN `user` TO `user_id`;
ALTER TABLE `reviews`   RENAME COLUMN `user` TO `user_id`;
ALTER TABLE `favorites` RENAME COLUMN `user` TO `user_id`;

-- #20: Fix typo nazionality → nationality
ALTER TABLE `brand`    RENAME COLUMN `nazionality` TO `nationality`;
ALTER TABLE `parfumer` RENAME COLUMN `nazionality` TO `nationality`;
