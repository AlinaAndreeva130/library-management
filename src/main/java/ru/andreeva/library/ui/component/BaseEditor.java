package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.util.Pair;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseEditor<T, ID, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>> extends Dialog implements KeyNotifier {
    private final R repository;
    private Binder<T> binder;
    private final Class<T> entityClass;
    private Runnable actionAfterEdit;
    private Runnable actionAfterAdd;
    private Map<String, Validator<?>> validatorMap;

    public BaseEditor(R repository, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.repository = repository;
        validatorMap = new HashMap<>();
        createClosePanel();
        VerticalLayout contentPanel = new VerticalLayout();
        add(contentPanel);
        createContentPanel(contentPanel);
        createActionPanel(this.repository);
    }

    public void addEntity(Runnable actionAfterAdd) {
        this.actionAfterAdd = actionAfterAdd;
        binder.setBean(createNewEntity());
        actionBeforeOpen();
        open();
    }

    public void editEntity(T entity, Runnable actionAfterEdit) {
        this.actionAfterEdit = actionAfterEdit;
        actionBeforeOpen();
        binder.setBean(entity);
        open();
    }

    public void deleteEntity(T entity, Runnable actionAfterDelete) {
        repository.delete(entity);
        actionAfterDelete.run();
    }

    protected abstract void createContentPanel(VerticalLayout contentPanel);

    protected abstract T createNewEntity();

    protected <TYPE> void addValidator(String fieldName, Validator<TYPE> validator) {
        validatorMap.put(fieldName, validator);
    }

    private void createActionPanel(JpaRepository repository) {
        Button saveBtn = new Button("Сохранить", event -> saveEntity(repository));
        Button cancelBtn = new Button("Отмена", event -> close());
        HorizontalLayout actionPanel = new HorizontalLayout(saveBtn, cancelBtn);
        add(actionPanel);
    }

    private void createClosePanel() {
        Button closeBtn = new Button(new Icon(VaadinIcon.CLOSE), event -> close());
        HorizontalLayout closePanel = new HorizontalLayout(closeBtn);
        closePanel.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        add(closePanel);
    }

    @PostConstruct
    private void postConstruct() {
        bindFields();
    }

    private void bindFields() {
        binder = new Binder<>(entityClass);
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .forEach(field -> {
                    String fieldName = field.getName();
                    try {
                        Bind annotation = field.getAnnotation(Bind.class);
                        String propertyName =
                                StringUtils.isNotEmpty(annotation.value()) ? annotation.value() : fieldName;
                        field.setAccessible(true);
                        Object fieldInstance = field.get(this);
                        if (fieldInstance == null) {
                            throw new IllegalArgumentException("Field must be initialized");
                        }

                        if (annotation.converter() != Bind.Converter.NONE) {
                            Pair<Converter, Object> converterNullRepresentation =
                                    getConverter(annotation.converter(), annotation.nullRepresentation());
                            binder.forField((HasValue<?, ?>) fieldInstance)
                                    .withConverter(converterNullRepresentation.getFirst())
                                    .withNullRepresentation(converterNullRepresentation.getSecond())
                                    .withValidator(getValidator(fieldName))
                                    .bind(propertyName);
                        } else {
                            binder.forField((HasValue<?, ?>) fieldInstance)
                                    .withValidator(getValidator(fieldName))
                                    .bind(propertyName);
                        }
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException("Binding class not contain filed " + fieldName);
                    } catch (ClassCastException e) {
                        throw new IllegalArgumentException("There is no possibility of binding a field of this type");
                    }
                });
    }

    private <TYPE> Validator<TYPE> getValidator(String fieldName) {
        Validator<TYPE> validator = (Validator<TYPE>) validatorMap.get(fieldName);
        if (validator != null) {
            return validator;
        }
        return (value, context) -> ValidationResult.ok();
    }

    private Pair<Converter, Object> getConverter(Bind.Converter converter, String nullRepresentation) {
        if (converter == Bind.Converter.STRING_TO_DOUBLE) {
            return Pair.of(new StringToDoubleConverter("Not a double"), Double.parseDouble(nullRepresentation));
        } else if (converter == Bind.Converter.STRING_TO_INTEGER) {
            return Pair.of(new StringToIntegerConverter("Not a number"), Integer.parseInt(nullRepresentation));
        } else if (converter == Bind.Converter.STRING_TO_BIG_DECIMAL) {
            return Pair.of(new StringToBigDecimalConverter("Not a big decimal"), BigDecimal.valueOf(Double.parseDouble(nullRepresentation)));
        } else {
            throw new IllegalArgumentException("Unsupported converter type");
        }
    }

    protected void actionBeforeOpen() {
        // maybe implementing
    }

    private void saveEntity(JpaRepository repository) {
        List<ValidationResult> validationErrors = binder.validate().getValidationErrors();
        if (!validationErrors.isEmpty()) {
            Notification.show("Некорректно заполнены поля", 3000, Notification.Position.TOP_STRETCH);
            return;
        }

        close();
        repository.save(binder.getBean());

        if (actionAfterAdd != null) {
            actionAfterAdd.run();
        }
        if (actionAfterEdit != null) {
            actionAfterEdit.run();
        }
    }
}
