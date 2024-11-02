-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************


CREATE USER itg_database_owner
    WITH PASSWORD 'password';

GRANT ALL
    ON ALL TABLES IN SCHEMA public
    TO itg_database_owner;

GRANT ALL
    ON ALL SEQUENCES IN SCHEMA public
    TO itg_database_owner;

CREATE USER itg_database_appuser
    WITH PASSWORD 'password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON ALL TABLES IN SCHEMA public
    TO itg_database_appuser;

GRANT USAGE, SELECT
    ON ALL SEQUENCES IN SCHEMA public
    TO itg_database_appuser;
