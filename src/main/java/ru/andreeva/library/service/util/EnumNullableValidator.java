package ru.andreeva.library.service.util;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class EnumNullableValidator<T> extends AbstractValidator<T> {
    public EnumNullableValidator(T type) {
        super("Обязательно к заполнению");
    }

    public EnumNullableValidator(T type, String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(T value, ValueContext context) {
        return toResult(value, value != null);
    }
}
