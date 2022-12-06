package com.nsu.rpgstats.viewmodel.user.validators;

public class EmailValidator extends UserInputValidator{
    public EmailValidator() {
        super("Wrong email format");
    }

    @Override
    public boolean isValid(CharSequence text) {
        String regex = "^(.+)@(\\S+)$";
        return text.length() > 0 && text.toString().matches(regex);
    }
}
