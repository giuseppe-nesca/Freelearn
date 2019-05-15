DROP TABLE Lessons;
DROP TABLE Courses;
DROP TABLE Subjects;
DROP TABLE Teachers;
DROP TABLE Users;

CREATE TABLE Users
(
  id       INT          NOT NULL AUTO_INCREMENT,
  name     VARCHAR(100) NOT NULL,
  surname  VARCHAR(100) NOT NULL,
  email    VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(200) NOT NULL,
  role     ENUM ('client', 'admin'),
  PRIMARY KEY (id)
);
/*generate 1 admin and 2 users*/
INSERT INTO Users (name, surname, email, password, role)
VALUES ('Giuseppe', 'Nesca', 'giuseppenesca@email.it', PASSWORD('sudo'), 'admin');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('John', 'Doe', 'johndoe@email.it', PASSWORD('john'), 'client');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('Mario', 'Rossi', 'mariorossi@email.it', PASSWORD('mario'), 'client');
;

CREATE TABLE Teachers
(
  id      INT          NOT NULL AUTO_INCREMENT,
  name    VARCHAR(100) NOT NULL,
  surname VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO Teachers (name, surname)
VALUES ("Albert", "Einstein");
INSERT INTO Teachers (name, surname)
VALUES ("Alan", "Touring");
INSERT INTO Teachers (name, surname)
VALUES ("Centola", "Matteo");

CREATE TABLE Subjects
(
  id   INT          NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);
INSERT INTO Subjects (name)
VALUES ('Fisica');
INSERT INTO Subjects (name)
VALUES ('Algoritmi');
INSERT INTO Subjects (name)
VALUES ('Database');

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
INSERT INTO Courses (teacherID, subjectID)
VALUES ("1", "1");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("2", "2");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("1", "3");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("3", "1");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("3", "2");
INSERT INTO Courses (teacherID, subjectID)
VALUES ("3", "3");


create table Lessons
(
  id int auto_increment,
  userID int not null,
  courseID int not null,
  date date not null,
  slot int not null,
  status enum("done", "booked", "cancelled") not null,
  FOREIGN KEY (userID) REFERENCES users(id) ON DELETE CASCADE ,
  FOREIGN KEY (courseID) REFERENCES courses(id) ON DELETE CASCADE ,
  PRIMARY KEY (id),
  UNIQUE (courseID, date, slot),
  UNIQUE (userID, date, slot)
);
INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (1, 2, "2019-02-12", 1, "booked");
INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (2, 1, "2019-02-12", 1, "booked");
INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (3, 1, "2019-02-12", 2, "booked");
INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (1, 1, "2019-02-13", 1, "booked");
