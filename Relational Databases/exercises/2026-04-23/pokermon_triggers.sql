USE pokemon_normalized;

DROP FUNCTION IF EXISTS get_team_size;

DELIMITER  //

CREATE FUNCTION get_team_size(team_id INTEGER)
RETURNS INTEGER

BEGIN

    DECLARE team_size INT;

    SELECT COUNT(*) INTO team_size
    FROM team_pokemon WHERE team_pokemon.team_id = team_id;

    RETURN team_size;

END //

DELIMITER ;

SELECT team_id, name, get_team_size(team_id) FROM teams;

DROP PROCEDURE IF EXISTS catch_pokemon;

DELIMITER //

CREATE PROCEDURE catch_pokemon(IN p_team_id INT, IN p_level INT, IN p_pokemon_id INT, IN p_item_id INT)

BEGIN
    DECLARE team_size INT;

    START TRANSACTION;

    SET team_size = get_team_size(p_team_id);

    IF team_size >= 6 THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Team already full';
    END IF;

    INSERT INTO team_pokemon(TEAM_ID, POSITION, LEVEL, POKEMON_ID, ITEM_ID)
    VALUES (p_team_id, team_size + 1, p_level, p_pokemon_id, p_item_id);

    COMMIT;

END //

DELIMITER ;

CALL catch_pokemon(1, 23, 27, 1);

SELECT * FROM team_pokemon WHERE team_id = 1;


SET @current_pos = 1;


DROP PROCEDURE IF EXISTS remove_pokemon;

DELIMITER //

CREATE PROCEDURE remove_pokemon(IN p_team_id INT, INOUT p_position INT)

BEGIN

    START TRANSACTION;

    DELETE FROM team_pokemon WHERE team_id = p_team_id AND position = p_position;

    UPDATE team_pokemon SET position = position - 1
    WHERE team_id = p_team_id AND position > p_position;

    SET p_position = get_team_size(p_team_id) + 1;

    COMMIT ;

END //

DELIMITER ;

CALL remove_pokemon(1, @current_pos);

SELECT @current_pos;

CREATE TABLE IF NOT EXISTS team_pokemon_log
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    action_type VARCHAR(10),
    pokemon_id INT,
    team_id INT,
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    info TEXT
);

DROP TRIGGER IF EXISTS trg_before_insert_team_pokemon;

DELIMITER //

CREATE TRIGGER trg_before_insert_team_pokemon
BEFORE INSERT ON team_pokemon
FOR EACH ROW

BEGIN

    IF NEW.level < 1 OR NEW.level > 100 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'LEVEL MUST BE BETWEEN 1 AND 100 DUMBASS';
    END IF;

END //

DELIMITER ;

INSERT INTO team_pokemon(team_id, position, level, pokemon_id, item_id)
VALUES (1, 1, 20, 1, 1);

INSERT INTO team_pokemon(team_id, position, level, pokemon_id, item_id)
VALUES (1, 1, -20, 1, 1);


DROP TRIGGER IF EXISTS trg_after_insert_team_pokemon;

DELIMITER //

CREATE TRIGGER trg_after_insert_team_pokemon
AFTER INSERT ON team_pokemon
FOR EACH ROW

BEGIN

    INSERT INTO team_pokemon_log(action_type, pokemon_id, team_id, info)
    VALUES ('INSERT', NEW.pokemon_id, new.team_id, CONCAT('INSERTED AT ', NEW.position, ' with level ', NEW.level, '.'));

END //

DELIMITER ;

INSERT INTO team_pokemon(team_id, position, level, pokemon_id, item_id)
VALUES (1, 1, 20, 1, 1);


DROP TRIGGER IF EXISTS trg_before_update_team_pokemon;

DELIMITER //

CREATE TRIGGER trg_before_update_team_pokemon
BEFORE UPDATE ON team_pokemon
FOR EACH ROW

BEGIN

    IF NEW.pokemon_id != OLD.pokemon_id THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'KDJGSOPFDJHFHJLFHDFHLKFLHFHKLFDHNJLFDNHKL';
    END IF;

END //

DELIMITER ;

UPDATE team_pokemon SET pokemon_id = 45 WHERE team_id = 1 AND position = 2;
UPDATE team_pokemon SET level = 150 WHERE team_id = 1 AND position = 2;

DROP TRIGGER IF EXISTS trg_after_update_team_pokemon;

DELIMITER //

CREATE TRIGGER trg_after_update_team_pokemon
AFTER UPDATE ON team_pokemon
FOR EACH ROW

BEGIN

    UPDATE team_pokemon SET pokemon_id = NEW.pokemon_id + 1
    WHERE team_id = NEW.team_id AND position > NEW.position;

END //

DELIMITER ;

UPDATE team_pokemon SET level = 150 WHERE team_id = 1 AND position = 2;

DROP TRIGGER IF EXISTS trg_after_insert_team_pokemon_log;

DELIMITER //

CREATE TRIGGER trg_after_insert_team_pokemon_log
AFTER INSERT ON team_pokemon_log
FOR EACH ROW

BEGIN

    UPDATE team_pokemon SET pokemon_id = NEW.pokemon_id + 1
    WHERE team_id = NEW.team_id;

END //

DELIMITER ;

UPDATE team_pokemon SET level = 150 WHERE team_id = 1 AND position = 2;

CREATE OR REPLACE VIEW team_summary AS
    SELECT team_id, name, get_team_size(team_id) AS team_size FROM teams;

CREATE OR REPLACE VIEW pokemon_nice AS
    SELECT pokedex_number, name, GROUP_CONCAT(type_name)
    FROM pokemon NATURAL JOIN pokemon_types
                 NATURAL JOIN types
    GROUP BY pokedex_number;