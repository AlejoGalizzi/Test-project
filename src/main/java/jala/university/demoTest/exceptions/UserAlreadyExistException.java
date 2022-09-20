package jala.university.demoTest.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        super("Email is already in use.");
    }

}
