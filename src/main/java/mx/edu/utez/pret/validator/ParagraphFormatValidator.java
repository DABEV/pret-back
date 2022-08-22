package mx.edu.utez.pret.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ParagraphFormatValidator implements ConstraintValidator<ParagraphFormat, String> {
    
    private final String PATTERN_STR = "[^À-ÿA-Za-z0-9\\.\\,\\+\\#\\s]+";

    @Override
    public boolean isValid(String paragraph, ConstraintValidatorContext context) {
        boolean isValid = false;

        if (paragraph == null || paragraph.length() == 0)
            return true;
        
        try {
            Pattern pattern = Pattern.compile(PATTERN_STR);
            Matcher matcher = pattern.matcher(paragraph);
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
