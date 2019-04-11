SELECT * FROM subjects;

INSERT INTO subjects (name) VALUE (?);

UPDATE subjects SET isActive = '0' WHERE id = ?;

SELECT isActive FROM subjects WHERE name = ?;

SELECT courses.id FROM courses WHERE courses.subjectID = ?;

SELECT isActive FROM subjects WHERE id = ?;