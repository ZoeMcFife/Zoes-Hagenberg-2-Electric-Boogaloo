USE pokemondb;

CREATE TABLE IF NOT EXISTS pokemon
(
    pokedex_number INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type_1 VARCHAR(50) NOT NULL,
    type_2 VARCHAR(50),
    hp INT,
    attack INT,
    defense INT,
    speed INT
);