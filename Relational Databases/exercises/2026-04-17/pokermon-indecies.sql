USE pokemon_normalized;

ALTER TABLE pokemon ADD COLUMN IF NOT EXISTS description TEXT NULL;

UPDATE pokemon SET description = 'SIIIIX SEVEN SIX SEVEN SIIIIX SEVEN is the best pokermon out there!'
WHERE pokedex_number = 67;

UPDATE pokemon SET description = 'The older brother of SIX SEVEN Pokermon. DEARTH'
WHERE pokedex_number = 69;

UPDATE pokemon SET description = 'Many idiots have fried frying this pokermon. its posoinous'
WHERE pokedex_number = 124;

UPDATE pokemon SET description = 'nobody cares about this pokermon. smelly'
WHERE pokedex_number = 437;

UPDATE pokemon SET description = 'Spaceballs the pokermon'
WHERE pokedex_number = 1;

UPDATE pokemon SET description = 'crushingly crushed by a crush (cruhs)'
WHERE pokedex_number = 45;

SELECT * FROM pokemon
WHERE description LIKE '%six%'
AND description NOT LIKE '%older%';

SELECT * FROM pokemon
WHERE description IS NOT NULL;

ANALYZE SELECT * FROM pokemon
WHERE description LIKE '%six%'
AND description NOT LIKE '%older%';

ANALYZE SELECT * FROM pokemon
WHERE description LIKE '%six%';

ALTER TABLE pokemon DROP INDEX IF EXISTS fulltext_description;

ALTER TABLE pokemon ADD FULLTEXT INDEX fulltext_description (description);

ANAlYZE SELECT * FROM pokemon
WHERE MATCH(description) AGAINST ('spaceballs AND SIX SEVEN' IN NATURAL LANGUAGE MODE);

ANAlYZE SELECT * FROM pokemon
WHERE MATCH(description) AGAINST ('+pokermon -SIX' IN BOOLEAN MODE);

SELECT name, MATCH(description) AGAINST ('spaceballs AND SIX SEVEN AND POKERMON' IN NATURAL LANGUAGE MODE) FROM pokemon
WHERE MATCH(description) AGAINST ('spaceballs AND SIX SEVEN AND POKERMON' IN NATURAL LANGUAGE MODE);