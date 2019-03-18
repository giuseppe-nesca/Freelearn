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
