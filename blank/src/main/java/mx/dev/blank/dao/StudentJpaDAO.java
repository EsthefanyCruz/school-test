package mx.dev.blank.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mx.dev.blank.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentJpaDAO implements StudentDAO {

  @Setter(onMethod = @__(@PersistenceContext), value = AccessLevel.PACKAGE)
  private EntityManager em;

  @Override
  public List<Student> getStudentsBySearchCriteria(
      final String nameQuery,
      final String uuidQuery,
      final Date rangeStart,
      final Date rangeEnd) {

    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<Student> query = builder.createQuery(Student.class);
    final Root<Student> root = query.from(Student.class);

    query.select(root);

    final List<Predicate> predicates = new ArrayList<>();
    predicates.add(builder.between(root.get(Student_.birthday), rangeStart, rangeEnd));

    buildSearchCriteria(predicates, builder, root, nameQuery, uuidQuery);

    return em.createQuery(query).getResultList();
  }

  private void buildSearchCriteria(
      final List<Predicate> predicates,
      final CriteriaBuilder builder,
      final Root<Student> root,
      final String nameQuery,
      final String uuidQuery) {

    if (StringUtils.isNotBlank(nameQuery)) {
      final String queryFormat = "%" + nameQuery + "%";

      predicates.add(builder.like(root.get(Student_.name), queryFormat));
      predicates.add(builder.like(root.get(Student_.firstSurname), queryFormat));
      predicates.add(builder.like(root.get(Student_.secondSurname), queryFormat));
    }

    if (uuidQuery != null && uuidQuery.trim().isEmpty()) {
      final String queryFormat = "%" + uuidQuery + "%";

      predicates.add(builder.like(root.get(Student_.uuid), queryFormat));
    }
  }

  /*
      select course.name
      from student
      inner join grade on grade.student_id = student.id
      inner join course_teacher on course_teacher.id = grade.course_teacher_id
      inner join teacher on teacher.id = course_teacher.teacher_id
      inner join course on course.id = course_teacher.course_id
      where student.uuid = "U-2";
      */

  /*Busca materias por UUID del estudiante*/
  public List<String> getCourseBySearchUUid(final String uuid) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<String> query = builder.createQuery(String.class);
    final Root<Grade> root = query.from(Grade.class);
    final Join<Grade, Student> gradeJoinStudent=root.join(String.valueOf(Grade_.student));
    final Join<Grade, CourseTeacher> gradeJoinCourseTeacher=root.join(Grade_.courseTeacher);
    final Join<CourseTeacher, Teacher> courseTeacherJoinTeacher=root.join(String.valueOf(CourseTeacher_.teacher));
    final Join<CourseTeacher, Course> courseTeacherJoinCourse=root.join(String.valueOf(CourseTeacher_.course));
    //query.select(courseTeacherJoinCourse.get(Course_.name));
    query
            .select(root.get(String.valueOf(Course_.name)))
            .where(builder.equal(root.get(String.valueOf(gradeJoinStudent.get(Student_.uuid))), uuid));
    //System.out.println(query.select(root).where(builder.equal(root.get(Student_.uuid), uuid)));
    return em.createQuery(query).getResultList();
  }

  /*Crear query en JPA Criteria API para obtener la lista de cursos con su maestro por UUID de estudiante.
  Sólo obtener nombre de curso y keycode de curso y nombre de teacher. Agregar método a StudentDAO*/
  public List<String> getSCoursesTeacherByUUID(final String uuid) {
    final CriteriaBuilder builder = em.getCriteriaBuilder();
    final CriteriaQuery<String> query = builder.createQuery(String.class);
    final Root<Student> root = query.from(Student.class);
    final Join<Grade, Student> gradeJoinStudent=root.join(String.valueOf(Grade_.student));
    final Join<Grade, CourseTeacher> gradeJoinCourseTeacher=root.join(String.valueOf(Grade_.courseTeacher));
    final Join<CourseTeacher, Teacher> courseTeacherJoinTeacher=root.join(String.valueOf(CourseTeacher_.teacher));
    final Join<CourseTeacher, Course> courseTeacherJoinCourse=root.join(String.valueOf(CourseTeacher_.course));
    query.multiselect(
            courseTeacherJoinCourse.get(Course_.name),
            courseTeacherJoinCourse.get(Course_.keycode),
            courseTeacherJoinTeacher.get(Teacher_.name));
    //query.select(root).where(builder.equal(root.get(Student_.uuid), uuid));
    query
            .select(root.get(String.valueOf(Course_.name)))
            .where(builder.equal(root.get(String.valueOf(gradeJoinStudent.get(Student_.uuid))), uuid));
    return em.createQuery(query).getResultList();
  }




}
