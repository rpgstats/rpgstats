package com.rpgstats.messages;

import com.rpgstats.exceptions.ModelException;

public class ItemNotFoundException extends ModelException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
