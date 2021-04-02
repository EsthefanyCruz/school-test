package mx.dev.blank.dao;
import mx.dev.blank.model.GradeCourseStudentDTO;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface GradeDAO {

    List<GradeCourseStudentDTO> getAssignedCourseTeacherStudentsByStudentId(
            int grade,
            @NotNull Date data_register,
            @NotNull int course_teacher,
            @NotNull int student_id
           );
}
