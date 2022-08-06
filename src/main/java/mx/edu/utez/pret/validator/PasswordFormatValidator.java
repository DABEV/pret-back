package mx.edu.utez.pret.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordFormatValidator implements ConstraintValidator<PasswordFormat, String> {

    private final String PATTERN_PHONE = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)(?=^.{6,}$).*$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid = false;

        if (password == null || password.length() == 0)
            return true;
        
        try {
            isValid = password.matches(PATTERN_PHONE);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }
}
