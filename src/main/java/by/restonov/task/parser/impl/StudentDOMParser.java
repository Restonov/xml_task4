package by.restonov.task.parser.impl;

import by.restonov.task.data.ArgumentName;
import by.restonov.task.entity.Student;
import by.restonov.task.exception.StudentParserException;
import by.restonov.task.parser.BaseParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * DOM parser class with <strong>factory</strong> field
 * @version 1.0
 */
public class StudentDOMParser implements BaseParser<Student> {
    private static final Logger logger = LogManager.getLogger();
    private static final int FIRST_ITEM = 0;
    DocumentBuilderFactory factory;

    /**
     * Constructor - making new parser object with factory initialization
     */
    public StudentDOMParser(){
        factory = DocumentBuilderFactory.newInstance();
    }

    /**
     * Method for parsing xml file data to Set of {@link Student}
     * @param xmlFile - path to xml file
     * @return Set of {@link Student}
     * @throws StudentParserException - common exception for all presented parsers
     */
    @Override
    public Set<Student> parseToStudentsSet(String xmlFile) throws StudentParserException {
        Set<Student> students = new HashSet<>();
        DocumentBuilder docBuilder;
        Document doc;
        try {
            docBuilder = factory.newDocumentBuilder();
            doc = docBuilder.parse(xmlFile);
            Element root = doc.getDocumentElement();
            NodeList studentsList = root.getElementsByTagName(ArgumentName.STUDENT);
            for (int i = 0; i < studentsList.getLength(); i++) {
                Element studentElement = (Element) studentsList.item(i);
                Student student = buildStudent(studentElement);
                students.add(student);
            }
        } catch (IOException e) {
            logger.error("File error or I/O error: " + e);
            throw new StudentParserException("File error or I/O error: " + e);
        } catch (SAXException e) {
            logger.error("Parsing failure: " + e);
            throw new StudentParserException("Parsing failure: " + e);
        } catch (ParserConfigurationException e) {
            logger.error("Parser config error: " + e);
            throw new StudentParserException("Parser config error: " + e);
        }
        return students;
    }

    /**
     * Method for parsing xml file data to console
     * @param xmlFile - path to xml file
     * not implemented for this parser type
     */
    @Override
    public void parseToConsole(String xmlFile) throws StudentParserException {
        throw new UnsupportedOperationException();
    }

    /**
     * Private method for creating new {@link Student} from doc element
     * @param studentElement - Element
     * @return new {@link Student}
     */
    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        student.setFaculty(studentElement.getAttribute(ArgumentName.FACULTY));
        student.setName(findElementTextContent(studentElement, ArgumentName.NAME));
        int tel = Integer.parseInt(findElementTextContent(studentElement,ArgumentName.TELEPHONE));
        student.setTelephone(tel);
        Student.Address address = student.getAddress();
        Element addressElement = (Element) studentElement.getElementsByTagName(ArgumentName.ADDRESS).item(FIRST_ITEM);
        address.setCountry(findElementTextContent(addressElement, ArgumentName.COUNTRY));
        address.setCity(findElementTextContent(addressElement, ArgumentName.CITY));
        address.setStreet(findElementTextContent(addressElement, ArgumentName.STREET));
        student.setLogin(studentElement.getAttribute(ArgumentName.LOGIN));
        return student;
    }

    /**
     * Private static method for receiving text element content by it's name
     * @param element - Element
     * @param elementName - name of Element
     * @return text element content
     */
    private static String findElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(FIRST_ITEM);
        return node.getTextContent();
    }
}
