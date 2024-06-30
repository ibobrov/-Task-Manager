create schema if not exists backend;

create table if not exists backend.users (
    id bigserial primary key,
    email varchar(256) not null unique,
    password varchar(256) not null,
    full_name text not null,
    confirmed_registration boolean not null default false,
    active boolean not null default false,
    role varchar(128) not null
);
