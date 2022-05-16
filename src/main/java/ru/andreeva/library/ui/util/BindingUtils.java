package ru.andreeva.library.ui.util;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import ru.andreeva.library.ui.component.Bind;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class BindingUtils {
    public static <T> void bindFields(Binder<T> binder,
                                      Map<String, Validator<?>> validatorMap,
                                      Object bindingObject) {
        Arrays.stream(bindingObject.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .forEach(field -> {
                    String fieldName = field.getName();
                    try {
                        Bind annotation = field.getAnnotation(Bind.class);
                        String propertyName =
                                StringUtils.isNotEmpty(annotation.value()) ? annotation.value() : fieldName;
                        field.setAccessible(true);
                        Object fieldInstance = field.get(bindingObject);
                        if (fieldInstance == null) {
                            throw new IllegalArgumentException("Field must be initialized");
                        }

                        if (annotation.converter() != Bind.Converter.NONE) {
                            Pair<Converter, Object> converterNullRepresentation =
                                    getConverter(annotation.converter(), annotation.nullRepresentation());
                            ((Binder<?>) binder).forField((HasValue<?, ?>) fieldInstance)
                                    .withConverter(converterNullRepresentation.getFirst())
                                    .withNullRepresentation(converterNullRepresentation.getSecond())
                                    .withValidator(getValidator(fieldName, validatorMap))
                                    .bind(propertyName);
                        } else {
                            ((Binder<?>) binder).forField((HasValue<?, ?>) fieldInstance)
                                    .withValidator(getValidator(fieldName, validatorMap))
                                    .bind(propertyName);
                        }
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("Binding class not contain filed " + fieldName);
                    } catch (ClassCastException e) {
                        throw new IllegalArgumentException("There is no possibility of binding a field of this type");
                    }
                });
    }

    private static <T> Validator<T> getValidator(String fieldName, Map<String, Validator<?>> validatorMap) {
        Validator<T> validator = (Validator<T>) validatorMap.get(fieldName);
        if (validator != null) {
            return validator;
        }
        return (value, context) -> ValidationResult.ok();
    }

    private static Pair<Converter, Object> getConverter(Bind.Converter converter, String nullRepresentation) {
        if (converter == Bind.Converter.STRING_TO_DOUBLE) {
            return Pair.of(new StringToDoubleConverter("Not a double"), Double.parseDouble(nullRepresentation));
        } else if (converter == Bind.Converter.STRING_TO_INTEGER) {
            return Pair.of(new StringToIntegerConverter("Not a number"), Integer.parseInt(nullRepresentation));
        } else if (converter == Bind.Converter.STRING_TO_BIG_DECIMAL) {
            return Pair.of(new StringToBigDecimalConverter("Not a big decimal"),
                    BigDecimal.valueOf(Double.parseDouble(nullRepresentation)));
        } else {
            throw new IllegalArgumentException("Unsupported converter type");
        }
    }
}
