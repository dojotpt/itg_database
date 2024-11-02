-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'itg_database';

DROP DATABASE itg_database;

DROP USER itg_database_owner;
DROP USER itg_database_appuser;
