CREATE TABLE actors
(
    id NUMBER(38) NOT NULL,
    name VARCHAR2(256),
    age NUMBER(3),
    CONSTRAINT id_pk_actor PRIMARY KEY (id)
);

CREATE TABLE directors
(
    id NUMBER(38) NOT NULL,
    name VARCHAR2(256),
    age NUMBER(3),
    CONSTRAINT id_pk_director PRIMARY KEY (id)
);

CREATE TABLE movies
(
    id NUMBER(38) NOT NULL,
    title VARCHAR2(256),
    release_date DATE,
    duration NUMBER(3),
    score NUMBER(*, 2),
    id_director NUMBER(38) NOT NULL,
    CONSTRAINT id_pk_mov PRIMARY KEY (id),
    CONSTRAINT fk_director FOREIGN KEY (id_director) REFERENCES directors(id)
);

CREATE TABLE genres
(
    id NUMBER(38) NOT NULL,
    name VARCHAR2(256),
    CONSTRAINT id_pk_gen PRIMARY KEY (id)
);

CREATE TABLE movie_genre
(
    id_movie NUMBER(38) NOT NULL,
    id_genre NUMBER(38) NOT NULL,
    CONSTRAINT movie_fk FOREIGN KEY (id_movie) REFERENCES movies(id),
    CONSTRAINT genre_fk FOREIGN KEY (id_genre) REFERENCES genres(id),
    CONSTRAINT unique_id UNIQUE (id_movie, id_genre)
);

CREATE TABLE movie_actor
(
    id_movie NUMBER(38) NOT NULL,
    id_actor NUMBER(38) NOT NULL,
    CONSTRAINT movie_act_fk FOREIGN KEY (id_movie) REFERENCES movies(id),
    CONSTRAINT actor_fk FOREIGN KEY (id_actor) REFERENCES actors(id),
    CONSTRAINT unique_act_id UNIQUE (id_movie, id_actor)
);