package ru.practicum.shareit.request.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {NonNegativeIntegerValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNegativeInteger {

    String message() default "Value must be greater than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
