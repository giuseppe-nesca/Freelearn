DROP TABLE Courses;
DROP TABLE Subjects;

CREATE TABLE Subjects
(
  id   INT          NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);

CREATE TABLE Courses
(
  id        INT NOT NULL AUTO_INCREMENT,
  teacherID INT NOT NULL,
  subjectID INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (teacherID) REFERENCES Teachers(id) ON DELETE CASCADE ,
  FOREIGN KEY (subjectID) REFERENCES Subjects(id) ON DELETE CASCADE,
  UNIQUE (teacherID, subjectID)
);

INSERT INTO Subjects (name)
VALUES ('Fisica');
INSERT INTO Subjects (name)
VALUES ('Algoritmi');
INSERT INTO Subjects (name)
VALUES ('Database');

INSERT INTO Courses (teacherID, subjectID)
VALUES ("1", "3");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("2", "2");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("1", "2");

SELECT * FROM Subjects;
SELECT * FROM Courses;

select *
from courses as c, teachers as t, Subjects as s
where c.teacherID = t.id AND c.subjectID = s.id and s.name="Algoritmi";

INSERT INTO courses (subjectID, teacherID, isActive) VALUES (?, ?, 1);

SELECT t.surname, t.name, s.name
FROM subjects as s, courses as c, teachers as t
WHERE c.subjectID = ? AND c.teacherID = ? AND c.subjectID = s.id AND c.teacherID = t.id;

SELECT c.id as courseID, t.id as teacherID, s.id as subjectID, t.surname as teacherSurname, t.name as teacherName, s.name as subjectName
FROM subjects as s, courses as c, teachers as t
WHERE c.subjectID = ? AND c.teacherID = ? AND c.subjectID = s.id AND c.teacherID = t.id;

SELECT c.id as courseID, t.id as teacherID, s.id as subjectID, t.surname as teacherSurname, t.name as teacherName, s.name as subjectName
FROM subjects as s, courses as c, teachers as t
WHERE c.subjectID = s.id AND c.teacherID = t.id;