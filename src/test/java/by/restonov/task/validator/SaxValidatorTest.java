package by.restonov.task.validator;

import by.restonov.task.data.ArgumentName;
import by.restonov.task.exception.StudentValidatorException;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SaxValidatorTest {
        SaxValidator validator;
        String xmlCorrectFile;
        String xmlIncorrectFile;
        String xsdFile;

    @BeforeClass
    public void setUp() {
        validator = new SaxValidator();
        xmlCorrectFile = ArgumentName.XML_FILE;
        xmlIncorrectFile = ArgumentName.XML_FILE_INCORRECT;
        xsdFile = ArgumentName.XSD_FILE;
    }

    @AfterClass
    public void tearDown() {
        validator = null;
        xmlCorrectFile = null;
        xmlIncorrectFile = null;
        xsdFile = null;
    }

    @Test
    public void validatePositiveTest() {
        boolean result = false;
        try {
            result = validator.validate(xmlCorrectFile, xsdFile);
        } catch (StudentValidatorException e) {
            e.printStackTrace();
        }
        AssertJUnit.assertTrue(result);
    }

    @Test
    public void validateNegativeTest() {
        boolean result = false;
        try {
            result = validator.validate(xmlIncorrectFile, xsdFile);
        } catch (StudentValidatorException e) {
            e.printStackTrace();
        }
        AssertJUnit.assertFalse(result);
    }
}
