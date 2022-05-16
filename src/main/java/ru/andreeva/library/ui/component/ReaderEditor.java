package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.ReaderRepository;
import ru.andreeva.library.ui.util.DateValidator;
import ru.andreeva.library.ui.util.IntegerValidator;
import ru.andreeva.library.ui.util.StringNullableValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @Bind(converter = Bind.Converter.STRING_TO_INTEGER)
    private TextField clazz;

    public ReaderEditor(ReaderRepository repository) {
        super(repository, Reader.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        firstName = new TextField("Фамилия");
        firstName.setRequired(true);
        firstName.setAutofocus(true);
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setWidthFull();
        addValidator("firstName", new StringNullableValidator());

        lastName = new TextField("Имя");
        lastName.setRequired(true);
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setWidthFull();
        addValidator("lastName", new StringNullableValidator());

        patronymic = new TextField("Отчество");
        patronymic.setValueChangeMode(ValueChangeMode.EAGER);
        patronymic.setWidthFull();

        clazz = new TextField("Класс");
        clazz.setRequired(true);
        clazz.setValueChangeMode(ValueChangeMode.EAGER);
        clazz.setWidthFull();
        addValidator("clazz", new IntegerValidator(1, 11, "возможно выбрать с 1-го по 11-й класс"));

        birthday = new DatePicker("День рождения");
        birthday.setRequired(true);
        birthday.setWidthFull();
        addValidator("birthday",
                new DateValidator(LocalDate.of(1900, 1, 1), LocalDate.now(),
                        "дата рождения может быть от 01.01.1900 до " + LocalDate.now().format(
                                DateTimeFormatter.ofPattern("d.MM.y"))));

        contentPanel.add(firstName, lastName, patronymic, clazz, birthday);
        contentPanel.setWidth("400px");
    }

    @Override
    protected Reader createNewEntity() {
        return new Reader();
    }
}
