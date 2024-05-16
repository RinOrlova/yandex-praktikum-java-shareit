package ru.practicum.shareit.booking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateEnumValidator implements ConstraintValidator<ValidStateEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidStateEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (acceptedValues.contains(value.toString())) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Unknown state: " + value)
                .addConstraintViolation();

        return false;
    }
}
