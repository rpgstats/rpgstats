--liquibase formatted sql

--changeset nomelyanenko:change_constr
ALTER TABLE users__sessions
    drop constraint users__sessions_session_id_fkey,
add constraint users__sessions_session_id_fkey
foreign key (session_id)
references sessions(id)
on delete cascade;