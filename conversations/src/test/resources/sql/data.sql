create schema if not exists public;
create table if not exists public.users
(
    id            uuid      default gen_random_uuid() not null
        primary key,
    name          varchar(30),
    points        bigint,
    sex           varchar(10),
    creation_date timestamp default now(),
    username      varchar(10),
    password      text
);


