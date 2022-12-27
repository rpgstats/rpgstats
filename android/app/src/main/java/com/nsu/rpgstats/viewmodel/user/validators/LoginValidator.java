package com.nsu.rpgstats.viewmodel.user.validators;

public class LoginValidator extends UserInputValidator{
    public LoginValidator() {
        super("Login length should be between 4 and 15");
    }

    @Override
    public boolean isValid(CharSequence text) {
        return text.length() >= 4 && text.length() <= 15;
    }
}
