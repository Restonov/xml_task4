package by.restonov.task.parser;

import by.restonov.task.exception.StudentParserException;

import java.util.Set;

public interface BaseParser<T> {
    Set<T> parseToStudentsSet(String xmlFile) throws StudentParserException;
    void parseToConsole(String xmlFile) throws StudentParserException;
}
