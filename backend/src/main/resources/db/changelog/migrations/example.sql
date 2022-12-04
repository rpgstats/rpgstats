--liquibase formatted sql

--changeset nomelyanenko:example-migr

INSERT INTO users(email, password, username)
VALUES ('DEFAULT', 'DEFAULT', 'DEFAULT')