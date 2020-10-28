package by.restonov.task.parser;

import by.restonov.task.data.ArgumentName;
import by.restonov.task.entity.Student;
import by.restonov.task.exception.StudentParserException;
import by.restonov.task.parser.impl.StudentDOMParser;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class StudentDOMParserTest extends Assert {
    StudentDOMParser parser;
    Set<Student> studentsSet;
    Student student1;
    Student student2;

    @BeforeClass
    public void setUp() {
        parser = new StudentDOMParser();
        studentsSet = new HashSet<>();
        student1 = new Student();
        student2 = new Student();
        student1.setLogin("Ivanov95");
        student1.setFaculty("geo");
        student1.setName("Ivanov Ivan");
        student1.setTelephone(291234567);
        student1.setAddress(new Student.Address("Belarus", "Minsk", "Prospekt Nezalejnosti 5"));
        student2.setLogin("SergeevAn");
        student2.setFaculty("ksis");
        student2.setName("Sergeev Andrey");
        student2.setTelephone(251275466);
        student2.setAddress(new Student.Address("Belarus", "Brest", "Lenina 2"));
        studentsSet.add(student1);
        studentsSet.add(student2);
    }

    @AfterClass
    public void tearDown() {
        parser = null;
        studentsSet = null;
        student1 = null;;
        student2 = null;;
    }

    @Test
    public void parseToStudentsSetPositiveTest() {
        Set<Student> expected = studentsSet;
        Set<Student> actual = null;
        try {
            actual = parser.parseToStudentsSet(ArgumentName.XML_FILE);
        } catch (StudentParserException e) {
            e.printStackTrace();
        }
        AssertJUnit.assertEquals(expected, actual);
    }

    @Test
    public void parseToStudentsSetNegativeTest() {
        Set<Student> expected = studentsSet;
        Set<Student> actual = null;
        try {
            actual = parser.parseToStudentsSet(ArgumentName.XML_FILE_INCORRECT);
        } catch (StudentParserException e) {
            e.printStackTrace();
        }
        AssertJUnit.assertNotSame(expected, actual);
    }
}