package com.si.siassignment.exceptions;

public abstract class ApplicationException extends RuntimeException{
    ApplicationException(String message) {
        super(message);
    }
}
