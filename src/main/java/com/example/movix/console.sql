create table users
(
    id bigserial  primary key ,
    login char(200) not null unique ,
    name char(200) not null ,
    email char(200) not null unique,
    birthday date not null
);
create table films
(
    id bigserial primary key,
    name char(200) ,
    description char(200) not null,
    releaseDate date not null ,
    duration int not null
);
create table users_friends
(
    user_id bigint references users(id),
    friend_id bigint references users(id)
);
create table likes(
    user_id bigint references users(id),
    film_id bigint references films(id)
);
create table genres(
    id bigserial primary key ,
    name char(200) not null unique
);
create table mpa (
    id bigserial primary key ,
    name char(200) not null unique
);
insert into mpa (id, name)
values (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

insert into genres (id, name)
values (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

create table films_genres
(
    film_id bigint references films(id),
    genre_id bigint references genres(id)
);
create table films_mpa(
    film_id bigint references films(id),
    mpa_id bigint references mpa(id)
);

