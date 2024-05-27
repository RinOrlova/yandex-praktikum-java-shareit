package ru.practicum.shareit.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NonNegativeIntegerValidator implements ConstraintValidator<NonNegativeInteger, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return value >= 0;
    }

}
