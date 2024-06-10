package ru.practicum.shareit.booking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StateEnumValidator.class)
public @interface ValidStateEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default "Unknown state: {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
