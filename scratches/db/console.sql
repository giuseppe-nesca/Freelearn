insert into lessons (userID, courseID, date, slot, status)
VALUES (1,2, "2019-02-10", 6, "booked");

select dayofweek("2019-02-10")


select c.id, t.id, t.surname, s.id, s.name from courses as c, subjects as s, teachers as t
where c.subjectID = s.id and c.teacherID = t.id