BEGIN;
-- Drop existing structures if they exist
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TYPE IF EXISTS status CASCADE;

-- Create an ENUM type
CREATE TYPE status AS ENUM ('WAITING', 'APPROVED', 'REJECTED', 'CANCELED');

-- Create tables without foreign keys to test basic functionality
CREATE TABLE IF NOT EXISTS users (id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE);

-- Add other tables one by one to isolate the issue
CREATE TABLE IF NOT EXISTS requests (id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, description VARCHAR(500), requestor_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS items (id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, name VARCHAR(100) NOT NULL, description VARCHAR(500), is_available BOOLEAN NOT NULL, owner_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE, request_id BIGINT REFERENCES requests (id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS bookings (id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL, end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL, item_id BIGINT NOT NULL REFERENCES items (id) ON DELETE CASCADE, booker_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE, status STATUS NOT NULL);

CREATE TABLE IF NOT EXISTS comments (id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, text VARCHAR(1000) NOT NULL, item_id BIGINT NOT NULL REFERENCES items (id) ON DELETE CASCADE, author_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE);

COMMIT;
