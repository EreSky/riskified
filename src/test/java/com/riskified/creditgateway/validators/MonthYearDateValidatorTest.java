package com.riskified.creditgateway.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonthYearDateValidatorTest {

    private final MonthYearDateValidator monthYearDateValidator = new MonthYearDateValidator();

    @Test
    public void WHEN_VALID_EXPIRATION_FORMAT_IS_VALID() {
        var result = monthYearDateValidator.isValid("10/21", null);
        assertTrue(result);
    }

    @Test
    public void WHEN_NOT_VALID_MONTH_EXPIRATION_FORMAT_IS_INVALID() {
        var result = monthYearDateValidator.isValid("101/21", null);
        assertFalse(result);
    }

    @Test
    public void WHEN_NOT_VALID_YEAR_EXPIRATION_FORMAT_IS_INVALID() {
        var result = monthYearDateValidator.isValid("10/2021", null);
        assertFalse(result);
    }

    @Test
    public void WHEN_NOT_VALID_EXPIRATION_FORMAT_IS_INVALID() {
        var result = monthYearDateValidator.isValid("10-21", null);
        assertFalse(result);
    }
}
