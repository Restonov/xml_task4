package by.restonov.task.entity;

import by.restonov.task.data.ArgumentName;

public enum StudentEnum {
    STUDENTS(ArgumentName.STUDENTS),
    LOGIN(ArgumentName.LOGIN),
    FACULTY(ArgumentName.FACULTY),
    STUDENT(ArgumentName.STUDENT),
    NAME(ArgumentName.NAME),
    TELEPHONE(ArgumentName.TELEPHONE),
    COUNTRY(ArgumentName.COUNTRY),
    CITY(ArgumentName.CITY),
    STREET(ArgumentName.STREET),
    ADDRESS(ArgumentName.ADDRESS);

    private String value;

    private StudentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
