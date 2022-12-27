--liquibase formatted sql

--changeset nomelyanenko:delete_link
ALTER TABLE sessions
DROP COLUMN connection_link;