package by.restonov.task.parser.impl;

import by.restonov.task.entity.Student;
import by.restonov.task.exception.StudentParserException;
import by.restonov.task.handler.StudentConsoleHandler;
import by.restonov.task.handler.StudentErrorHandler;
import by.restonov.task.handler.StudentSetHandler;
import by.restonov.task.parser.BaseParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

/**
 * SAX parser class with <strong>factory</strong> field
 * @version 1.0
 */
public class StudentSaxParser implements BaseParser<Student> {
    private static final Logger logger = LogManager.getLogger();
    private SAXParserFactory factory;

    /**
     * Constructor - making new parser object with factory initialization
     */
    public StudentSaxParser() {
        factory = SAXParserFactory.newInstance();
    }

    /**
     * Method for parsing xml file to console
     * @param xmlFile - path to xml file
     * @throws StudentParserException - common exception for all presented parsers
     */
    public void parseToConsole(String xmlFile) throws StudentParserException {
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new StudentConsoleHandler());
            reader.setErrorHandler(new StudentErrorHandler());
            reader.parse(xmlFile);
        } catch (SAXException e) {
            logger.error("SAX parser error: " + e);
            throw new StudentParserException("SAX parser error: " + e);
        } catch (IOException e) {
            logger.error("IO error " + e);
            throw new StudentParserException("SAX parser error: " + e);
        } catch (ParserConfigurationException e) {
            logger.error("Parser config error " + e);
            throw new StudentParserException("Parser config error " + e);
        }
    }

    /**
     * Method for parsing xml file data to Set of {@link Student}
     * @param xmlFile - path to xml file
     * @return Set of {@link Student}
     * @throws StudentParserException - common exception for all presented parsers
     */
    @Override
    public Set<Student> parseToStudentsSet(String xmlFile) throws StudentParserException {
        StudentSetHandler handler;
        try {
            handler = new StudentSetHandler();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new StudentErrorHandler());
            reader.parse(xmlFile);
        } catch (SAXException e) {
            logger.error("SAX parser error: " + e);
            throw new StudentParserException("SAX parser error: " + e);
        } catch (IOException e) {
            logger.error("IO error " + e);
            throw new StudentParserException("SAX parser error: " + e);
        } catch (ParserConfigurationException e) {
            logger.error("Parser config error " + e);
            throw new StudentParserException("Parser config error " + e);
        }
        return handler.getStudents();
    }
}
