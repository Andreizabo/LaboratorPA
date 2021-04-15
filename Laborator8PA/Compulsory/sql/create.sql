CREATE TABLE movies
(
    id NUMBER(38) NOT NULL,
    title VARCHAR2(256),
    release_date DATE,
    duration NUMBER(3),
    score NUMBER(*, 2),
    CONSTRAINT id_pk_mov PRIMARY KEY (id)
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