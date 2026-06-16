CREATE TABLE IF NOT EXISTS pokemon (
    pokedex_number INT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    type1          VARCHAR(50)  NOT NULL,
    type2          VARCHAR(50),
    hp             INT          NOT NULL,
    attack         INT          NOT NULL,
    defense        INT          NOT NULL,
    speed          INT          NOT NULL
);
