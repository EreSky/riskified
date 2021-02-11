package com.riskified.creditgateway.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MonthYearDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonthYear {
    String message() default "invalidDateFormat";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
