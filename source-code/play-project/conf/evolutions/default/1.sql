# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table URLs (
  id                        bigserial not null,
  owner                     varchar(255),
  generated                 varchar(255),
  original                  varchar(255),
  constraint pk_URLs primary key (id))
;

create table Users (
  id                        bigserial not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_Users primary key (id))
;




# --- !Downs

drop table if exists URLs cascade;

drop table if exists Users cascade;

