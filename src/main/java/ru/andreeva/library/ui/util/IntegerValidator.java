package ru.andreeva.library.ui.util;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class IntegerValidator extends AbstractValidator<Integer> {
    private final int start;
    private final int end;

    public IntegerValidator(int start, int end, String errorMessage) {
        super(errorMessage);
        this.start = start;
        this.end = end;
    }

    @Override
    public ValidationResult apply(Integer value, ValueContext context) {
        return toResult(value, value != null && value >= start && value <= end);
    }
}
