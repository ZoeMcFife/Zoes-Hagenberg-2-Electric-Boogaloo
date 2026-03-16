USE Pokemon_HYP;

SELECT  AVG(attack) AS average_attack,
        AVG(defense) AS average_defense,
        AVG(speed) AS average_speed
FROM pokemon;

SELECT COUNT(name) FROM pokemon
WHERE attack > (SELECT AVG(attack) FROM pokemon);

SELECT AVG(attack) AS average_attack, type_name FROM pokemon
JOIN Pokemon_HYP.types t ON t.type_id = pokemon.type1_id
GROUP BY type1_id
HAVING average_attack > (SELECT AVG(attack) FROM pokemon)
ORDER BY average_attack DESC;

SELECT t.type_name, t2.type_name, COUNT(p.name) AS pokemon_count
FROM pokemon p
JOIN Pokemon_HYP.types t ON t.type_id = p.type1_id
JOIN Pokemon_HYP.types t2 on t2.type_id = p.type2_id
GROUP BY t.type_name, t2.type_name
ORDER BY pokemon_count DESC;

SELECT o1.type_name, (o1.pokemon_count + o2.pokemon_count) AS total_pokemon_count
FROM
(SELECT t.type_name as type_name, COUNT(p.name) AS pokemon_count
FROM pokemon p
JOIN Pokemon_HYP.types t ON t.type_id = p.type1_id
GROUP BY t.type_name
ORDER BY pokemon_count DESC) as o1

JOIN

(SELECT t2.type_name as type_name, COUNT(p.name) AS pokemon_count
FROM pokemon p
JOIN Pokemon_HYP.types t2 ON t2.type_id = p.type2_id
GROUP BY t2.type_name
ORDER BY pokemon_count DESC) as o2
ON o1.type_name = o2.type_name

GROUP BY o1.type_name;

SELECT pokemon.name, type1_id, type2_id FROM pokemon
WHERE
    type1_id IN (select most_common_type.type_1_id from most_common_type)
AND
    type2_id IN (select most_common_type.type_2_id from most_common_type);

DROP VIEW IF EXISTS most_common_type;

CREATE VIEW IF NOT EXISTS most_common_type AS
SELECT t.type_name as type_1_name, t2.type_name as type_2_name, t.type_id as type_1_id, t2.type_id as type_2_id, COUNT(p.name) AS pokemon_count
FROM pokemon p
         JOIN Pokemon_HYP.types t ON t.type_id = p.type1_id
         JOIN Pokemon_HYP.types t2 on t2.type_id = p.type2_id
GROUP BY t.type_name, t2.type_name
ORDER BY pokemon_count DESC
LIMIT 1;

SELECT * FROM most_common_type;

SELECT t.type_name as type_1_name, t2.type_name as type_2_name, t.type_id as type_1_id, t2.type_id as type_2_id, COUNT(p.name) AS pokemon_count
FROM pokemon p
         JOIN Pokemon_HYP.types t ON t.type_id = p.type1_id
         JOIN Pokemon_HYP.types t2 on t2.type_id = p.type2_id
GROUP BY t.type_name, t2.type_name
ORDER BY pokemon_count DESC;