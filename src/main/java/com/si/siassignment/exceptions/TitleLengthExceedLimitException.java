package com.si.siassignment.exceptions;

import com.si.siassignment.models.Clipboard;

public class TitleLengthExceedLimitException extends ApplicationException{
    public TitleLengthExceedLimitException() {
        super("Title length has a limit of - "+ Clipboard.TITLE_LIMIT );
    }
}
