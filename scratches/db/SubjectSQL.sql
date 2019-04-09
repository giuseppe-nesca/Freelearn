SELECT * FROM subjects;

INSERT INTO subjects (name) VALUE (?);

UPDATE subjects SET isActive = '0' WHERE id = ?;

SELECT isActive FROM subjects WHERE name = ?;