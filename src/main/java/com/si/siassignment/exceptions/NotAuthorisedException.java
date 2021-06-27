package com.si.siassignment.exceptions;

public class NotAuthorisedException extends ApplicationException{
    public NotAuthorisedException() {
        super("Request not authorised");
    }
}
