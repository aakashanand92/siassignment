package com.si.siassignment.exceptions;

public class WrongPasswordException extends ApplicationException {
    public WrongPasswordException() {
        super("Password entered is wrong");
    }
}
