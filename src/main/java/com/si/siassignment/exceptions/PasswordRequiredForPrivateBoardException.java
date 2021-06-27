package com.si.siassignment.exceptions;

public class PasswordRequiredForPrivateBoardException extends ApplicationException {
    public PasswordRequiredForPrivateBoardException() {
        super("For a private board password is required, please provide one");
    }
}
