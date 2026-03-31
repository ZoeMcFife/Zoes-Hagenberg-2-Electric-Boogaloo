DROP DATABASE IF EXISTS mmo_db;

CREATE DATABASE IF NOT EXISTS mmo_db;

USE mmo_db;

CREATE TABLE guild
(
    guild_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) UNIQUE NOT NULL,
    founding_year INTEGER,
    motto VARCHAR(500)
);

-- The IS_PART_OF relationship is 1:N and can be represented as
-- a nullable foreign key in the player table!

CREATE TABLE player
(
    player_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(120) UNIQUE NOT NULL,
    current_level INTEGER NOT NULL,

    guild_id INTEGER,

    CONSTRAINT fk_player_guild
    FOREIGN KEY (guild_id)
    REFERENCES guild(guild_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE resource
(
    resource_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL
);

CREATE TABLE climate
(
    climate_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    climate_type VARCHAR(120) NOT NULL,
    climate_description VARCHAR(500) NOT NULL
);


-- A Biome has only one resource that can be harvested, so it references it as a foreign key
-- A biome also only has one climate, so it also just references it as a fk

CREATE TABLE biome
(
    biome_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,

    resource_id INTEGER,
    climate_id INTEGER,

    CONSTRAINT fk_biome_resource
    FOREIGN KEY (resource_id)
    REFERENCES resource(resource_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

    CONSTRAINT fk_biome_climate
    FOREIGN KEY (climate_id)
    REFERENCES climate(climate_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- The is_in_biome relationship is 1:N, so territory needs a foreign key to biome!

CREATE TABLE territory
(
    territory_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,

    biome_id INTEGER,

    CONSTRAINT fk_territory_biome
    FOREIGN KEY (biome_id)
    REFERENCES biome(biome_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- since we have a N:M relationship here, a new table is needed

CREATE TABLE is_bordering
(
    territory_id_1 INTEGER,
    territory_id_2 INTEGER,

    PRIMARY KEY (territory_id_1, territory_id_2),

    CONSTRAINT fk_is_bordering_territory_1
    FOREIGN KEY (territory_id_1)
    REFERENCES territory(territory_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    CONSTRAINT fk_is_bordering_territory_2
    FOREIGN KEY (territory_id_2)
    REFERENCES territory(territory_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- had to rename to player_rank since rank is a reserved keyword
CREATE TABLE player_rank
(
    rank_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    permission_level INTEGER NOT NULL,
    name VARCHAR(120) NOT NULL
);

-- this is a triple relationship N:M:O
-- I chose player and territory as primary IDs, since i don't want a player to be assigned to the same territory twice with different ids
-- it makes more sense that way...

CREATE TABLE assigned
(
    player_id INTEGER,
    territory_id INTEGER,
    rank_id INTEGER,

    PRIMARY KEY (player_id, territory_id),

    contribution_points INTEGER,
    assignment_date DATETIME,

    CONSTRAINT fk_assigned_player
    FOREIGN KEY (player_id)
    REFERENCES player(player_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    CONSTRAINT fk_assigned_territory
    FOREIGN KEY (territory_id)
    REFERENCES territory(territory_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    CONSTRAINT fk_assigned_rank
    FOREIGN KEY (rank_id)
    REFERENCES player_rank (rank_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- Example data
INSERT INTO resource (name) VALUES
                                ('Iron Ore'),
                                ('Gold Vein'),
                                ('Crystal Shard'),
                                ('Ancient Wood'),
                                ('Obsidian'),
                                ('Silver Ore'),
                                ('Mana Herb'),
                                ('Dragon Scale'),
                                ('Coal'),
                                ('Mythril');

INSERT INTO climate (climate_type, climate_description) VALUES
                                                            ('Temperate', 'Mild climate with balanced seasons'),
                                                            ('Arid', 'Dry desert climate with little rainfall'),
                                                            ('Tropical', 'Hot and humid with dense vegetation'),
                                                            ('Frozen', 'Extremely cold with snow and ice'),
                                                            ('Volcanic', 'Hot terrain with lava flows'),
                                                            ('Swamp', 'Wetlands with murky water'),
                                                            ('Mountain', 'High altitude with rocky terrain'),
                                                            ('Magical', 'Unstable magical anomalies present');

INSERT INTO biome (name, resource_id, climate_id) VALUES
                                                      ('Greenfields', 1, 1),
                                                      ('Golden Dunes', 2, 2),
                                                      ('Emerald Jungle', 7, 3),
                                                      ('Frost Peaks', 6, 4),
                                                      ('Ashen Wastes', 5, 5),
                                                      ('Murk Swamp', 7, 6),
                                                      ('Highrock Mountains', 9, 7),
                                                      ('Arcane Valley', 3, 8);

INSERT INTO territory (name, biome_id) VALUES
                                           ('Northwatch', 1),
                                           ('Sunspire', 2),
                                           ('Verdant Reach', 3),
                                           ('Icefang Hold', 4),
                                           ('Cinderfall', 5),
                                           ('Bogroot', 6),
                                           ('Stonecrest', 7),
                                           ('Mystvale', 8),
                                           ('Ironpass', 7),
                                           ('Dunewatch', 2);

INSERT INTO is_bordering VALUES
                             (1,2),(2,3),(3,4),(4,5),
                             (5,6),(6,7),(7,8),(8,1),
                             (1,3),(2,4),(5,7),(6,8);

INSERT INTO guild (name, founding_year, motto) VALUES
                                                   ('Iron Legion', 1203, 'Strength Above All'),
                                                   ('Golden Trade Co.', 1450, 'Profit is Power'),
                                                   ('Arcane Order', 980, 'Knowledge is Eternal'),
                                                   ('Shadow Syndicate', 1320, 'From the Shadows We Strike'),
                                                   ('Frostwolves', 1105, 'Endure and Conquer');

INSERT INTO player (username, current_level, guild_id) VALUES
                                                           ('Astra', 45, 1),
                                                           ('Blaze', 32, 1),
                                                           ('Cypher', 60, 3),
                                                           ('Dusk', 28, 4),
                                                           ('Ember', 50, 2),
                                                           ('Fang', 38, 5),
                                                           ('Glint', 22, NULL),
                                                           ('Hex', 70, 3),
                                                           ('Ivy', 15, NULL),
                                                           ('Jax', 55, 2),
                                                           ('Kora', 48, 5),
                                                           ('Lumen', 66, 3),
                                                           ('Mako', 30, 1),
                                                           ('Nyx', 41, 4),
                                                           ('Orin', 27, NULL);

INSERT INTO player_rank (permission_level, name) VALUES
                                                     (1, 'Recruit'),
                                                     (2, 'Member'),
                                                     (3, 'Veteran'),
                                                     (4, 'Officer'),
                                                     (5, 'Commander'),
                                                     (6, 'Guild Master');
INSERT INTO assigned
(player_id, territory_id, rank_id, contribution_points, assignment_date) VALUES
                                                                             (1,1,4,1200,'2025-01-10'),
                                                                             (2,1,2,400,'2025-02-15'),
                                                                             (3,8,5,3000,'2024-12-01'),
                                                                             (4,6,3,900,'2025-03-05'),
                                                                             (5,2,4,1500,'2025-01-20'),
                                                                             (6,4,3,800,'2025-02-01'),
                                                                             (7,3,NULL,100,'2025-03-10'),
                                                                             (8,8,6,5000,'2024-11-11'),
                                                                             (9,5,NULL,50,'2025-03-12'),
                                                                             (10,2,5,2000,'2025-01-25'),
                                                                             (11,4,4,1700,'2025-02-18'),
                                                                             (12,8,5,3200,'2025-01-05'),
                                                                             (13,9,2,600,'2025-02-22'),
                                                                             (14,6,3,950,'2025-02-28'),
                                                                             (15,7,NULL,200,'2025-03-15');

-- Views

CREATE VIEW v_player_overview AS
SELECT
    p.player_id,
    p.username,
    p.current_level,
    g.name AS guild_name,
    t.name AS territory_name,
    r.name AS rank_name,
    a.contribution_points
FROM player p
         LEFT JOIN guild g ON p.guild_id = g.guild_id
         LEFT JOIN assigned a ON p.player_id = a.player_id
         LEFT JOIN territory t ON a.territory_id = t.territory_id
         LEFT JOIN player_rank r ON a.rank_id = r.rank_id;

CREATE VIEW v_top_players AS
SELECT
    p.player_id,
    p.username,
    SUM(a.contribution_points) AS total_contribution
FROM player p
         JOIN assigned a ON p.player_id = a.player_id
GROUP BY p.player_id, p.username
ORDER BY total_contribution DESC;

CREATE VIEW v_territory_control AS
SELECT
    t.territory_id,
    t.name AS territory_name,
    COUNT(a.player_id) AS player_count,
    SUM(a.contribution_points) AS total_contribution
FROM territory t
         LEFT JOIN assigned a ON t.territory_id = a.territory_id
GROUP BY t.territory_id, t.name;

CREATE VIEW v_territory_details AS
SELECT
    t.territory_id,
    t.name AS territory_name,
    b.name AS biome_name,
    r.name AS resource,
    c.climate_type
FROM territory t
         JOIN biome b ON t.biome_id = b.biome_id
         LEFT JOIN resource r ON b.resource_id = r.resource_id
         LEFT JOIN climate c ON b.climate_id = c.climate_id;

CREATE VIEW v_guild_strength AS
SELECT
    g.guild_id,
    g.name AS guild_name,
    COUNT(p.player_id) AS member_count,
    SUM(a.contribution_points) AS total_contribution
FROM guild g
         LEFT JOIN player p ON g.guild_id = p.guild_id
         LEFT JOIN assigned a ON p.player_id = a.player_id
GROUP BY g.guild_id, g.name
ORDER BY total_contribution DESC;

CREATE VIEW v_unassigned_players AS
SELECT
    p.player_id,
    p.username
FROM player p
         LEFT JOIN assigned a ON p.player_id = a.player_id
WHERE a.player_id IS NULL;


CREATE VIEW v_territory_borders AS
SELECT
    t1.name AS territory,
    t2.name AS bordering_territory
FROM is_bordering ib
         JOIN territory t1 ON ib.territory_id_1 = t1.territory_id
         JOIN territory t2 ON ib.territory_id_2 = t2.territory_id;

CREATE VIEW v_high_rank_assignments AS
SELECT
    p.username,
    t.name AS territory,
    r.name AS rank,
    r.permission_level
FROM assigned a
         JOIN player p ON a.player_id = p.player_id
         JOIN territory t ON a.territory_id = t.territory_id
         JOIN player_rank r ON a.rank_id = r.rank_id
WHERE r.permission_level >= 4;