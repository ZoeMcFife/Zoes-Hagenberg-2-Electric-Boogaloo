SET search_path TO public;

DROP TABLE IF EXISTS game_genres;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS games;

CREATE TABLE genres
(
    genre_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE games
(
    game_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    release_date DATE,
    price DECIMAL(1000,2),
    review_score INT CHECK (review_score > 0 AND review_score < 100)
);

CREATE TABLE game_genres
(
    game_id INT REFERENCES games (game_id),
    genre_id INT REFERENCES genres (genre_id),

    PRIMARY KEY (game_id, genre_id)
);
