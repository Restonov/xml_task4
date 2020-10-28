package by.restonov.task.handler;

import by.restonov.task.data.ArgumentName;
import by.restonov.task.entity.Student;
import by.restonov.task.entity.StudentEnum;
import by.restonov.task.parser.impl.StudentSaxParser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Set handler for {@link StudentSaxParser}
 * All methods overrides DefaultHandler methods
 * provides Set of {@link Student}
 */
public class StudentSetHandler extends DefaultHandler {
    private static final int STUDENT_LOGIN = 0;
    private static final int STUDENT_FACULTY = 1;
    private static final int ATTRS_MAX_VALUE = 2;
    private Set<Student> students;
    private Student student = null;
    private StudentEnum currentEnum = null;
    private EnumSet<StudentEnum> withText;

    public StudentSetHandler() {
        students = new HashSet<>();
        withText = EnumSet.range(StudentEnum.NAME, StudentEnum.STREET);
    }
    public Set<Student> getStudents() {
        return students;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (qName.equals(ArgumentName.STUDENT)) {
            student = new Student();
            student.setLogin(attrs.getValue(STUDENT_LOGIN));
            if (attrs.getLength() == ATTRS_MAX_VALUE) {
                student.setFaculty(attrs.getValue(STUDENT_FACULTY));
            }
        } else {
            StudentEnum temp = StudentEnum.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(ArgumentName.STUDENT)) {
            students.add(student);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case NAME:
                    student.setName(s);
                    break;
                case TELEPHONE:
                    student.setTelephone(Integer.parseInt(s));
                    break;
                case STREET:
                    student.getAddress().setStreet(s);
                    break;
                case CITY:
                    student.getAddress().setCity(s);
                    break;
                case COUNTRY:
                    student.getAddress().setCountry(s);
                    break;
                default: throw new EnumConstantNotPresentException(
                        currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }
}
