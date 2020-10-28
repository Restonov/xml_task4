package by.restonov.task.validator;

import by.restonov.task.exception.StudentParserException;
import by.restonov.task.exception.StudentValidatorException;
import by.restonov.task.handler.StudentErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * XML file validator
 * @version 1.0
 */
public class SaxValidator {
        private static final Logger logger = LogManager.getLogger();

    /**
     * Method for xml validation via provided schema
     * @param xmlFile - path to xml file
     * @param xsdFile - path to xsd file
     * @return validation result - boolean
     * @throws StudentValidatorException - validator exception
     */
        public boolean validate(String xmlFile, String xsdFile) throws StudentValidatorException {
            boolean result = false;
            Schema schema;
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            StudentErrorHandler errorHandler = new StudentErrorHandler();
            try {
                schema = factory.newSchema(new File(xsdFile));
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                spf.setSchema(schema);
                SAXParser parser = spf.newSAXParser();
                parser.parse(xmlFile, errorHandler);
                if (errorHandler.wasActivated()) {
                    logger.info(xmlFile + " is not valid");
                } else {
                    logger.info(xmlFile + " is valid");
                    result = true;
                }
            } catch (ParserConfigurationException e) {
                logger.error(xmlFile + " config error: " + e.getMessage());
                throw new StudentValidatorException(e);
            } catch (IOException e) {
                logger.error("I/O error: " + e.getMessage());
                throw new StudentValidatorException(e);
            } catch (org.xml.sax.SAXException e) {
                logger.error("SAX error: " + e.getMessage());
                throw new StudentValidatorException(e);
            }
            return result;
        }
}
