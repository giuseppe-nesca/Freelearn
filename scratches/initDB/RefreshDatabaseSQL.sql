INSERT INTO Users (name, surname, email, password, role)
VALUES ('Giuseppe', 'Nesca', 'giuseppenesca@email.it', PASSWORD('sudo'), 'admin');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('John', 'Doe', 'johndoe@email.it', PASSWORD('john'), 'client');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('Mario', 'Rossi', 'mariorossi@email.it', PASSWORD('mario'), 'client');
;

INSERT INTO Teachers (name, surname, isActive)
VALUES ("Albert", "Einstein", 1);
INSERT INTO Teachers (name, surname, isActive)
VALUES ("Alan", "Touring",1 );
INSERT INTO Teachers (name, surname, isActive)
VALUES ("Matteo", "Centola", 1);

INSERT INTO Subjects (name, isActive)
VALUES ('Fisica', 1);
INSERT INTO Subjects (name, isActive)
VALUES ('Algoritmi', 1);
INSERT INTO Subjects (name, isActive)
VALUES ('Database', 1);

INSERT INTO Courses (teacherID, subjectID, isActive)
VALUES ("1", "1", 1);
INSERT INTO Courses (teacherID, subjectID, isActive)
VALUES ("2", "2", 1);
INSERT INTO Courses (teacherID, subjectID, isActive)
VALUES ("1", "3", 1);
INSERT INTO Courses (teacherID, subjectID, isActive)
VALUES ("3", "3", 1);

INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (1, 2, "2019-02-12", 1, "booked");
INSERT INTO Lessons (userID, courseID, date, slot, status)
VALUES (2, 1, "2019-08-12", 1, "booked");