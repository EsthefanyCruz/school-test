CREATE TABLE grade(
      id int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
      grade int(10),
      data_register date NOT NULL,
      course_teacher_id int(10) UNSIGNED NOT NULL,
      student_id int(10) UNSIGNED NOT NULL,
      PRIMARY KEY (id),
      FOREIGN KEY (course_teacher_id) REFERENCES course_teacher(id),
      FOREIGN KEY (student_id) REFERENCES student(id)
) ENGINE=InnoDB;
