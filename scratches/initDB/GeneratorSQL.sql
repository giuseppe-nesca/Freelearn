create table subjects
(
  id       int auto_increment
    primary key,
  name     varchar(200)         not null,
  isActive tinyint(1) default 1 null
);

create table teachers
(
  id       int auto_increment
    primary key,
  name     varchar(100)         not null,
  surname  varchar(100)         not null,
  isActive tinyint(1) default 1 null
);

create table courses
(
  id        int auto_increment
    primary key,
  teacherID int                  not null,
  subjectID int                  not null,
  isActive  tinyint(1) default 1 not null,
  constraint courses_ibfk_1
    foreign key (teacherID) references teachers (id)
      on delete cascade,
  constraint courses_ibfk_2
    foreign key (subjectID) references subjects (id)
      on delete cascade
);

create index subjectID
  on courses (subjectID);

create table users
(
  id       int auto_increment
    primary key,
  name     varchar(100)             not null,
  surname  varchar(100)             not null,
  email    varchar(100)             not null,
  password varchar(200)             not null,
  role     enum ('client', 'admin') null,
  constraint email
    unique (email)
);

create table lessons
(
  id       int auto_increment
    primary key,
  userID   int                          not null,
  courseID int                          not null,
  date     date                         not null,
  slot     int                          not null,
  status   enum ('booked', 'cancelled') not null,
  constraint lessons_ibfk_1
    foreign key (userID) references users (id)
      on delete cascade,
  constraint lessons_ibfk_2
    foreign key (courseID) references courses (id)
      on delete cascade
);


