INSERT INTO teacher_role (name, teacher_id) values ("Secretario", 1);
INSERT INTO teacher_role (name, teacher_id) values ("Profesor", 1);
INSERT INTO teacher_role (name, teacher_id) values ("Director", 2);
INSERT INTO teacher_role (name, teacher_id) values ("Profesor", 2);

INSERT INTO course_teacher(course_id, teacher_id, room_id, start_time, end_time, day) values (1, 1, 1, "10:00:00", "11:30:00", "Monday");
INSERT INTO course_teacher(course_id, teacher_id, room_id, start_time, end_time, day) values (2, 2, 2, "11:00:00", "12:30:00", "Tuesday");
INSERT INTO course_teacher(course_id, teacher_id, room_id, start_time, end_time, day) values (3, 3, 3, "12:00:00", "13:30:00", "Wednesday");
INSERT INTO course_teacher(course_id, teacher_id, room_id, start_time, end_time, day) values (4, 4, 4, "13:00:00", "14:30:00", "Thursday");

INSERT INTO student(uuid, name, first_surname, second_surname, birthday) values ("U-1", "Jose", "Lopez", "Santos", '1990-01-01');
INSERT INTO student(uuid, name, first_surname, second_surname, birthday) values ("U-2", "Sendy", "Cruz", "Sanchez", '1995-05-30');
INSERT INTO student(uuid, name, first_surname, second_surname, birthday) values ("U-3", "Aylin", "Rios", "Mendez", '1990-08-04');
INSERT INTO student(uuid, name, first_surname, second_surname, birthday) values ("U-4", "Carlos", "Ramos", "Romero", '1990-11-04');
INSERT INTO student(uuid, name, first_surname, second_surname, birthday) values ("U-5", "Daniel", "Duran", "Fuentes", '1993-04-13');
