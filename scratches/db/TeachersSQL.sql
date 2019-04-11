DROP TABLE Teachers;
CREATE TABLE Teachers
(
  id      INT          NOT NULL AUTO_INCREMENT,
  name    VARCHAR(100) NOT NULL,
  surname VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);
/*insert*/
INSERT INTO Teachers (name, surname)
VALUES ("Albert", "Einstein");
INSERT INTO Teachers (name, surname)
VALUES ("Alan", "Touring");

/*getall*/
SELECT *
FROM Teachers;
/*getbyid*/
SELECT *
FROM Teachers
WHERE id = 1;

/*getFull*/
SELECT courses.id AS courseID, teacherID, subjectID, s.name AS subjectName, t.name AS teacherName, surname
FROM Courses
  LEFT JOIN subjects s on courses.subjectID = s.id
  RIGHT JOIN teachers t on courses.teacherID = t.id
;

/*get teachers by subject*/
SELECT
       courses.id as courseID,
       Teachers.name as teacherName,
       Teachers.surname as teacherSurname,
       teacherID, subjectID
FROM Teachers, courses, subjects
WHERE subjects.id = 1 AND courses.subjectID = subjects.id AND courses.teacherID = teachers.id;

SELECT * FROM lessons WHERE lessons.date = ?;

SELECT * FROM lessons, courses WHERE lessons.date = ? AND courses.teacherID = ? AND lessons.courseID = courses.id;

SELECT lessons.slot FROM lessons, courses WHERE lessons.date = ? AND courses.teacherID = ? AND lessons.courseID = courses.id;

SELECT isActive FROM teachers WHERE surname = ?;

INSERT INTO teachers (surname, name) VALUE (?, ?);

SELECT * FROM teachers WHERE isActive = true;