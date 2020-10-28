package by.restonov.task.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.restonov.task.validator.SaxValidator;

/**
 * Error handler for parsers and validator
 * All methods overrides DefaultHandler methods
 * Log errors if validation not successful
 */
public class StudentErrorHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger();
    private boolean wasActivated;

    /**
    * default constructor
     * by default error handler "state" - wasn't activated
    * */
    public StudentErrorHandler() {
        wasActivated = false;
    }

    /**
     * If error handler was activated in {@link SaxValidator}
     * logger prints error "xml file is not valid"
     * or else "xml file valid"
     * @return result
     */
    public boolean wasActivated() {
        return wasActivated;
    }

    public void warning(SAXParseException e) {
            logger.warn(getLineAddress(e) + "-" + e.getMessage());
            wasActivated = true;
        }

        public void error(SAXParseException e) {
            logger.error(getLineAddress(e) + " - " + e.getMessage());
            wasActivated = true;
        }

        public void fatalError(SAXParseException e) {
            logger.fatal(getLineAddress(e) + " - " + e.getMessage());
            wasActivated = true;
        }

        private String getLineAddress(SAXParseException e) {
            return e.getLineNumber() + " : " + e.getColumnNumber();
        }
}

