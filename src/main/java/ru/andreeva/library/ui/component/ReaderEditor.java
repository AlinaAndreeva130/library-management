package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Reader;
import ru.andreeva.library.service.repository.ReaderRepository;

@SpringComponent
@UIScope
public class ReaderEditor extends BaseEditor<Reader, Long, ReaderRepository> {
    @Bind
    private TextField firstName;
    @Bind
    private TextField lastName;
    @Bind
    private TextField patronymic;
    @Bind
    private DatePicker birthday;
    @Bind(converter = Bind.Converter.STRING_TO_INTEGER, nullRepresentation = "1")
    private TextField clazz;

    public ReaderEditor(ReaderRepository repository) {
        super(repository, Reader.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        firstName = new TextField("Фамилия");
        firstName.setAutofocus(true);
        lastName = new TextField("Имя");
        patronymic = new TextField("Отчество");
        clazz = new TextField("Класс");
        birthday = new DatePicker("День рождения");
        birthday.addValueChangeListener(event -> {
            event.getValue();
        });
        contentPanel.add(firstName, lastName, patronymic, clazz, birthday);
    }

    @Override
    protected Reader createNewEntity() {
        return new Reader();
    }
}
