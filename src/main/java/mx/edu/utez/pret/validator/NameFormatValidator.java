package mx.edu.utez.pret.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameFormatValidator implements ConstraintValidator<NameFormat, String> {
    
    private static final String PATTERN_STR = "([^A-Za-zÀ-ÿ\\s\\.]+)";

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        boolean isValid = false;

        if (name == null || name.length() == 0)
            return true;
        
        try {
            Pattern pattern = Pattern.compile(PATTERN_STR);
            Matcher matcher = pattern.matcher(name);
            isValid = getCountInvalidChars(matcher) == 0;
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }

    private int getCountInvalidChars (Matcher matcher) {
        int count = 0;
        while(matcher.find()) count++;
        return count;
    }
}
