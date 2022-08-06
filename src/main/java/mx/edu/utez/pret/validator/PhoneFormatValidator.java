package mx.edu.utez.pret.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneFormatValidator implements ConstraintValidator<PhoneFormat, String> {

    private final String PATTERN_PHONE = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        boolean isValid = false;

        if (phone == null || phone.length() == 0)
            return true;
        
        try {
            isValid = phone.matches(PATTERN_PHONE);
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }
}
