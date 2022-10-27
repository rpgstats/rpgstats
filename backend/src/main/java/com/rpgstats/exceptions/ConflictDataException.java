package com.rpgstats.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ConflictDataException extends ModelException{
    public ConflictDataException(String message) {
        super(message);
    }

    public ConflictDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
