package by.restonov.task.handler;

import by.restonov.task.parser.impl.StudentSaxParser;
import by.restonov.task.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Console handler for {@link StudentSaxParser}
 * All methods overrides DefaultHandler methods
 * prints {@link Student} info tp console
 */
public class StudentConsoleHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void startDocument(){
        logger.info("Parsing started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        StringBuilder tagData = new StringBuilder(qName + " ");
        for (int i = 0; i < attributes.getLength(); i++) {
            tagData.append(" ").append(attributes.getQName(i)).append("=").append(attributes.getValue(i));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        System.out.println(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        System.out.println(" " + qName);
    }

    @Override
    public void endDocument(){
        logger.info("Parsing completed");
    }
}
