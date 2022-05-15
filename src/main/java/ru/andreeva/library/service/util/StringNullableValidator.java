package ru.andreeva.library.service.util;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;

public class StringNullableValidator extends AbstractValidator<String> {
    public StringNullableValidator() {
        super("Обязательно к заполнению");
    }

    public StringNullableValidator(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(value, !StringUtils.isEmpty(value));
    }
}
