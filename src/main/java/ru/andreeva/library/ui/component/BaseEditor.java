package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
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
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.ui.util.BindingUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseEditor<T, ID, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>> extends Dialog {
    private final R repository;
    @Getter
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
        createActionPanel();
        UI.getCurrent().addShortcutListener(() -> {
            if (isOpened()) {
                saveEntity();
            }
        }, Key.ENTER);
        binder = new Binder<>(entityClass);
    }

    public void addEntity(Runnable actionAfterAdd) {
        this.actionAfterAdd = actionAfterAdd;
        binder.removeBean();
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

    protected void actionBeforeOpen() {
        // maybe implementing
    }

    protected void actionAfterSave(T entity) {

    }

    protected void saveEntity() {
        List<ValidationResult> validationErrors = binder.validate().getValidationErrors();
        if (!validationErrors.isEmpty()) {
            Notification.show("Некорректно заполнены поля", 3000, Notification.Position.TOP_STRETCH);
            return;
        }

        close();
        actionAfterSave((T) repository.save(binder.getBean()));

        if (actionAfterAdd != null) {
            actionAfterAdd.run();
        }
        if (actionAfterEdit != null) {
            actionAfterEdit.run();
        }
    }

    private void createActionPanel() {
        Button saveBtn = new Button("Сохранить", event -> saveEntity());
        Button cancelBtn = new Button("Отмена", event -> close());
        HorizontalLayout actionPanel = new HorizontalLayout(saveBtn, cancelBtn);
        actionPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
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
        BindingUtils.bindFields(binder, validatorMap, this);
    }

}
