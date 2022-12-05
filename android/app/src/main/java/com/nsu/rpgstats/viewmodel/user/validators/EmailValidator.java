package com.nsu.rpgstats.viewmodel.user.validators;

public class EmailValidator extends UserInputValidator{
    public EmailValidator() {
        super("Wrong email format");
    }

    @Override
    public boolean isValid(CharSequence text) {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$]";
        return text.length() > 0 && text.toString().matches(regex);
    }
}
