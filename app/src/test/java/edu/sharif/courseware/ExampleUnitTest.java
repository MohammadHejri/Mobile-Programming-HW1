package edu.sharif.courseware;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import edu.sharif.courseware.model.Student;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Student sadegh = Student.createStudent("sadegh", "salam", "Sadegh", "Majidi", "98123456");
        assertNotNull(sadegh);
        assertEquals("sadegh", sadegh.getUsername());
        assertEquals("salam", sadegh.getPassword());
    }
}