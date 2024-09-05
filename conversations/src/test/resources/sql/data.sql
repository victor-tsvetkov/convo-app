create schema if not exists public;
create table public.users
(
    id            uuid      default random_uuid() not null
        primary key,
    name          varchar(30),
    points        bigint,
    sex           varchar(10),
    creation_date timestamp default now(),
    username      varchar(10),
    password      text
);