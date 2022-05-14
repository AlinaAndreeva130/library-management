package ru.andreeva.library.ui.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bind {
    String value() default "";
    Converter converter() default Converter.NONE;

    enum Converter {
        NONE,
        STRING_TO_INTEGER,
        STRING_TO_DOUBLE
    }
}