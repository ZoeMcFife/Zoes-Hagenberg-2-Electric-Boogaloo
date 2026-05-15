DROP DATABASE IF EXISTS game_db;

CREATE DATABASE game_db;

CREATE TABLE genre
(
    genre_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE game
(
    game_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    release_date DATE,
    price DECIMAL(1000,2),
    review_score INT CHECK (review_score > 0 AND review_score < 100)
);

CREATE TABLE game_genres
(
    game_id INT REFERENCES game (game_id),
    genre_id INT REFERENCES genre (genre_id),

    PRIMARY KEY (game_id, genre_id)
);