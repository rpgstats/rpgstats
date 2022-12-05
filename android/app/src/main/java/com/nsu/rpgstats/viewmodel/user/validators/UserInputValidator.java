package com.nsu.rpgstats.viewmodel.user.validators;

public abstract class UserInputValidator {
    private String errorString;

    public UserInputValidator(String err) {
        errorString = err;
    }

    public String getErrorString() {
        return errorString;
    }

    public abstract boolean isValid(CharSequence text);
}
