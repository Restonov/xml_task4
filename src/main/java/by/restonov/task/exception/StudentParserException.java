package by.restonov.task.exception;

public class StudentParserException extends Exception {
    public StudentParserException() {
    }

    public StudentParserException(String message) {
        super(message);
    }

    public StudentParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentParserException(Throwable cause) {
        super(cause);
    }
}
