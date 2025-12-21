create table users
(
    id bigserial  primary key ,
    login varchar(200) not null unique ,
    name varchar(200) not null ,
    email varchar(200) not null unique,
    birthday date not null
);
create table mpa (
                     id bigserial primary key ,
                     name varchar(200) not null unique
);
create table films
(
    id bigserial primary key,
    name varchar(200) ,
    description varchar(200) not null,
    release_date date not null ,
    duration int not null,
    mpa bigint references mpa(id)
);
create table users_friends
(
    user_id bigint references users(id),
    friend_id bigint references users(id),
    primary key (user_id,friend_id)
);
create table likes(
    user_id bigint references users(id),
    film_id bigint references films(id),
    primary key (user_id,film_id)
);
create table genres(
    id bigserial primary key ,
    name varchar(200) not null unique
);
create table films_genres
(
    film_id bigint references films(id),
    genre_id bigint references genres(id),
    primary key (film_id,genre_id)
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
