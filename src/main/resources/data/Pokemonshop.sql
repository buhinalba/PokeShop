alter table if exists POKEMON_CATEGORY drop constraint IF EXISTS fk_pokemon_id CASCADE ;
alter table if exists POKEMON_CATEGORY drop constraint IF EXISTS fk_category_id CASCADE ;
drop table if exists POKEMON cascade;
drop table if exists POKEMON_CATEGORY cascade;
drop table if exists CATEGORY cascade;
drop table if exists "USER" cascade;
drop table if exists CART cascade;
drop table if exists SHIPPING_INFO cascade;
drop table if exists PAYMENT_INFO cascade;
drop table if exists ORDER_HISTORY cascade;
drop table if exists ORDER_CONTENTS cascade;

DROP SEQUENCE IF EXISTS pokemon_id_seq;
DROP SEQUENCE IF EXISTS category_id_seq;
CREATE SEQUENCE pokemon_id_seq;
CREATE SEQUENCE category_id_seq;


create table POKEMON (
  id         int not null DEFAULT nextval('pokemon_id_seq'),
  name       varchar(255),
  price      int,
  sprite_url varchar(255));


create table POKEMON_CATEGORY (
  pokemon_id  int4 not null,
  category_id int4 not null);


create table CATEGORY (
  id   int not null DEFAULT nextval('category_id_seq'),
  name varchar(255)
  );


create table "USER" (
  id       int4 not null,
  name     varchar(255),
  email    varchar(255),
  password varchar(255)
  );


create table CART (
  user_id    int4,
  pokemon_id int4,
  count      int4);


create table SHIPPING_INFO (
  city    varchar(255),
  state   varchar(255),
  address varchar(255),
  zip     varchar(255));


create table PAYMENT_INFO (
  card_number     int4,
  card_name       varchar(255),
  expiration_date varchar(255),
  cvv             int4);


create table ORDER_HISTORY (
  id       int4 not null,
  "date"   timestamp,
  user_id  int4,
  "Column" int4);


create table ORDER_CONTENTS (
  order_id   int4,
  pokemon_id int4,
  count      int4);

alter table POKEMON add constraint pk_pokemon_id primary key (id);
alter table CATEGORY add constraint pk_category_id primary key (id);

alter table POKEMON_CATEGORY add constraint fk_pokemon_id foreign key (pokemon_id) references POKEMON (id);
alter table POKEMON_CATEGORY add constraint fk_category_id foreign key (category_id) references CATEGORY (id);
