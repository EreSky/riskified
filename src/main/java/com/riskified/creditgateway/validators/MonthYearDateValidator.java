package com.riskified.creditgateway.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MonthYearDateValidator implements ConstraintValidator<MonthYear, String> {

    private final static String dateFormat = "MM/yy";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            YearMonth yearMonth = YearMonth.parse(value, DateTimeFormatter.ofPattern(dateFormat));
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
