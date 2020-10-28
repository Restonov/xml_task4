package by.restonov.task.parser.impl;

import by.restonov.task.data.ArgumentName;
import by.restonov.task.entity.Student;
import by.restonov.task.entity.StudentEnum;
import by.restonov.task.exception.StudentParserException;
import by.restonov.task.parser.BaseParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * STAX parser class with <strong>factory</strong> field
 * @version 1.0
 */
public class StudentStaxParser implements BaseParser<Student> {
    private static final Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    /**
     * Constructor - making new parser object with factory initialization
     */
    public StudentStaxParser() {
        inputFactory = XMLInputFactory.newInstance();
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
        FileInputStream inputStream = null;
        XMLStreamReader reader;
        String name;
        try {
            inputStream = new FileInputStream(new File(xmlFile));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (StudentEnum.valueOf(name.toUpperCase()) == StudentEnum.STUDENT) {
                        Student st = buildStudent(reader);
                        students.add(st);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            logger.error("StAX parsing error! " + ex.getMessage());
            throw new StudentParserException("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            logger.error("File " + xmlFile + " not found! " + ex);
            throw new StudentParserException("File " + xmlFile + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("Impossible close file " + xmlFile + " : " + e);
                throw new StudentParserException("Impossible close file " + xmlFile + " : " + e);
            }
        }
        return students;
    }

    /**
     * Method for parsing xml file data to console
     * @param xmlFile - path to xml file
     * not implemented for this parser type
     */
    @Override
    public void parseToConsole(String xmlFile) {
        throw new UnsupportedOperationException();
    }

    /**
     * Private method for creating new {@link Student} from xml data
     * @param reader - XMLStreamReader
     * @return new {@link Student}
     * @throws XMLStreamException - stream exception
     */
    private Student buildStudent(XMLStreamReader reader) throws XMLStreamException {
        Student st = new Student();
        st.setLogin(reader.getAttributeValue(null, ArgumentName.LOGIN));
        st.setFaculty(reader.getAttributeValue(null, ArgumentName.FACULTY));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (StudentEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            st.setName(findXMLText(reader));
                            break;
                        case TELEPHONE:
                            name = findXMLText(reader);
                            st.setTelephone(Integer.parseInt(name));
                            break;
                        case ADDRESS:
                            st.setAddress(findXMLAddress(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (StudentEnum.valueOf(name.toUpperCase()) == StudentEnum.STUDENT) {
                        return st;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Student");
    }

    /**
     * Private method for receiving {@link Student.Address} from xml data
     * @param reader - XMLStreamReader
     * @return new {@link Student.Address}
     * @throws XMLStreamException - stream exception
     */
    private Student.Address findXMLAddress(XMLStreamReader reader) throws XMLStreamException {
        Student.Address address = new Student.Address();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (StudentEnum.valueOf(name.toUpperCase())) {
                        case COUNTRY:
                            address.setCountry(findXMLText(reader));
                            break;
                        case CITY:
                            address.setCity(findXMLText(reader));
                            break;
                        case STREET:
                            address.setStreet(findXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (StudentEnum.valueOf(name.toUpperCase()) == StudentEnum.ADDRESS) {
                        return address;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Address");
    }

    /**
     * Private method for receiving text from xml data
     * @param reader - XMLStreamReader
     * @return String data
     * @throws XMLStreamException - stream exception
     */
    private String findXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}