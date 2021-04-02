package mx.dev.blank.dao;
import com.beust.jcommander.internal.Lists;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import mx.dev.blank.DAOTestConfig;
import mx.dev.blank.DBTestConfig;
import mx.dev.blank.dao.StudentDAO;
import mx.dev.blank.dao.StudentJpaDAO;
import mx.dev.blank.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {DAOTestConfig.class, DBTestConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})


public class TestDAO extends AbstractTransactionalTestNGSpringContextTests {
    private static final String DBUNIT_XML = "classpath:dbunit/dao/data.xml";

    @Autowired
    private StudentJpaDAO studentJpaDAO;

    @DataProvider
    public Object[][] getCourseBySearchUUid_dataProvider() {
        List<String> courseNames = Lists.newArrayList("HISTORIA", "MATEMATICAS");

        return new Object[][]{
                {"U-1", courseNames}
        };
    }

    @Test(dataProvider = "getCourseBySearchUUid_dataProvider")
    @DatabaseSetup(DBUNIT_XML)
    public void getCourseBySearchUUid(final String studentUuid, List<String> expectedCourses) {
        final List<String> course = studentJpaDAO.getCourseBySearchUUid(studentUuid);

        assertThat(course).isNotNull();
        assertThat(course)
                .containsExactlyInAnyOrderElementsOf(Collections.singleton(studentUuid));
    }


    @Test(dataProvider = "findByID_dataProvider")
    @DatabaseSetup(DBUNIT_XML)
    public void getSCoursesTeacherByUUID(final String uuid) {
        //  uuid = "U-1";
        final List<String> course = studentJpaDAO.getSCoursesTeacherByUUID(uuid);
        assertThat(course)
                .containsExactlyInAnyOrderElementsOf(Collections.singleton(uuid));
    }


}

