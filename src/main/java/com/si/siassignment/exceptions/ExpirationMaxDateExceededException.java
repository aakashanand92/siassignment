package com.si.siassignment.exceptions;

import java.util.Date;

public class ExpirationMaxDateExceededException extends Exception{
    public ExpirationMaxDateExceededException() {
        super("Expiration Date can be max 90 days from today");
    }
}
