USE pokemon_normalized;

-- end transaction with `ROLLBACK`

-- commit transaction with `COMMIT`

START TRANSACTION;

INSERT INTO pokemon VALUES (999, 'Subi', 35);

SELECT * FROM pokemon WHERE pokedex_number = 999;

SELECT COUNT(*) INTO @team_size FROM team_pokemon WHERE team_id = 1;

IF @team_size >= 6 THEN
    ROLLBACK;
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Team is already full';
ELSE
    INSERT INTO team_pokemon(team_id, position, level, pokemon_id, item_id)
    VALUES (1, @team_size + 1, 10, 25, 3);
END IF;

SELECT * FROM team_pokemon WHERE team_id = 1;

ROLLBACK;

-- pokermon delete

START TRANSACTION;

DELETE FROM team_pokemon
WHERE team_id = 1 AND position = 2;

UPDATE team_pokemon SET position = position - 1
WHERE team_id = 1 AND position > 2;

SELECT * FROM team_pokemon WHERE  team_id = 1;

ROLLBACK;