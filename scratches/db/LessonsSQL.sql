# lessons
drop table lessons;
create table lessons
(
  id int auto_increment,
  userID int not null,
  courseID int not null,
  date date not null CHECK ( DAYOFWEEK(date) != 1 ),
  slot int not null CHECK (slot >= 1 AND slot <= 4),
  status enum("done", "booked", "cancelled") not null,
  FOREIGN KEY (userID) REFERENCES users(id) ON DELETE CASCADE ,
  FOREIGN KEY (courseID) REFERENCES courses(id) ON DELETE CASCADE ,
  PRIMARY KEY (id),
  UNIQUE (courseID, date, slot),
  UNIQUE (userID, date, slot)
);

create unique index lessons_id_uindex
  on lessons (id);


insert into lessons (userID, courseID, date, slot, status)
VALUES (4, 1, "2019-03-14", 2, "booked");

insert into lessons (userID, courseID, date, slot, status)
VALUES (4, 2, "2019-03-14", 1, "booked");

insert into lessons (userID, courseID, date, slot, status)
VALUES (4, 2, "2019-03-15", 1, "booked");

insert into lessons (userID, courseID, date, slot, status)
VALUES (2, 1, "2019-03-15", 2, "booked");