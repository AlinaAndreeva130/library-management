package ru.andreeva.library.ui.util;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.time.LocalDate;

public class DateValidator extends AbstractValidator<LocalDate> {
    private final LocalDate start;
    private final LocalDate end;

    public DateValidator() {
        super("Обязательно к заполнению");
        this.start = LocalDate.MIN;
        this.end = LocalDate.MAX;
    }

    public DateValidator(LocalDate start, LocalDate end, String errorMessage) {
        super(errorMessage);

        if (start == null) {
            throw new IllegalArgumentException("Start date must be not null");
        }
        this.start = start;

        if (end == null) {
            throw new IllegalArgumentException("End date must be not null");
        }
        this.end = end;
    }

    @Override
    public ValidationResult apply(LocalDate value, ValueContext context) {
        return toResult(value,
                value != null && (value.isEqual(start) || value.isAfter(start)) && (value.isEqual(
                        end) || value.isBefore(end)));
    }
}
