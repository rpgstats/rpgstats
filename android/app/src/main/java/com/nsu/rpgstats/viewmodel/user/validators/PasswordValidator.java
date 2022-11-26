package com.nsu.rpgstats.viewmodel.user.validators;

public class PasswordValidator extends UserInputValidator{
    public PasswordValidator() {
        super("Login length should be between 6 and 16");
    }

    @Override
    public boolean isValid(CharSequence text) {
        return text.length() >= 6 && text.length() <= 16;
    }
}
