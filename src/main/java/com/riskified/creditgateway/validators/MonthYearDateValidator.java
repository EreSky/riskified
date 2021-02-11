package com.riskified.creditgateway.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MonthYearDateValidator implements ConstraintValidator<MonthYear, String> {

    private final static String dateFormat = "MM/YY";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(value);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
