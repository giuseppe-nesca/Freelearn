/*setup*/
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
VALUES ('Giuseppe', 'Nesca', 'giuseppenesca@email.it', PASSWORD('admin'), 'admin');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('John', 'Doe', 'johndoe@email.it', PASSWORD('john'), 'client');
;
INSERT INTO Users (name, surname, email, password, role)
VALUES ('Mario', 'Rossi', 'mariorossi@email.it', PASSWORD('mario'), 'client');
;

/*GET ALL USERS*/
SELECT *
FROM Users;
/*GET USER by id*/
SELECT *
FROM Users
WHERE id = '1';
/*GET USER by emailaddress*/
SELECT *
FROM Users
WHERE email = 'giuseppenesca@email.it';

SELECT PASSWORD('sudo');

SELECT *
FROM Users
WHERE email = 'giuseppenesca@email.it'
AND password = PASSWORD('sudo');

SELECT *
FROM Users, lessons
WHERE Users.id = 1 AND Users.id = lessons.userID
;

UPDATE Users
SET password = PASSWORD('sudo')
WHERE email = 'giuseppenesca@email.it';

DELETE FROM Users WHERE id=1;