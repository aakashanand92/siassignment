package com.si.siassignment.exceptions;

public class NotAuthorisedException extends RuntimeException{
    public NotAuthorisedException() {
        super("Request not authorised");
    }
}
