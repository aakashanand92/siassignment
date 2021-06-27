package com.si.siassignment.exceptions;

import com.si.siassignment.models.Clipboard;

public class TextContentLengthExceededException extends  ApplicationException {
    public TextContentLengthExceededException() {
        super("Clipboard text content has a limit of - " + Clipboard.TEXT_LIMIT);
    }
}
