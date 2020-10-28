package by.restonov.task.exception;

public class StudentValidatorException extends Exception {
    public StudentValidatorException() {
    }

    public StudentValidatorException(String message) {
        super(message);
    }

    public StudentValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentValidatorException(Throwable cause) {
        super(cause);
    }
}
