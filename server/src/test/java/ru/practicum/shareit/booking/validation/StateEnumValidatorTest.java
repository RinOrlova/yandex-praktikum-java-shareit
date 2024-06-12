package ru.practicum.shareit.booking.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.enums.State;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateEnumValidatorTest {

    private StateEnumValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @ValidStateEnum(enumClass = State.class)
    private String state;

    @BeforeEach
    void setUp() {
        validator = new StateEnumValidator();
        ValidStateEnum annotation = getValidStateEnumAnnotation();
        validator.initialize(annotation);
    }

    private ValidStateEnum getValidStateEnumAnnotation() {
        try {
            return this.getClass().getDeclaredField("state").getAnnotation(ValidStateEnum.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isValid_shouldReturnTrueForValidEnumValue() {
        boolean result = validator.isValid("ALL", context);

        assertThat(result).isTrue();
        verifyNoInteractions(context);
    }

    @Test
    void isValid_shouldReturnTrueForAnotherValidEnumValue() {
        boolean result = validator.isValid("CURRENT", context);

        assertThat(result).isTrue();
        verifyNoInteractions(context);
    }

    @Test
    void isValid_shouldReturnTrueForNullValue() {
        boolean result = validator.isValid(null, context);

        assertThat(result).isTrue();
        verifyNoInteractions(context);
    }

    @Test
    void isValid_shouldReturnFalseForInvalidEnumValue() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);

        boolean result = validator.isValid("INVALID_STATE", context);

        assertThat(result).isFalse();
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("Unknown state: INVALID_STATE");
        verify(violationBuilder).addConstraintViolation();
    }
}
