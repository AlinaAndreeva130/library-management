package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.User;
import ru.andreeva.library.service.repository.UserRepository;
import ru.andreeva.library.ui.util.DateValidator;
import ru.andreeva.library.ui.util.StringNullableValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringComponent
@UIScope
public class UserEditor extends BaseEditor<User, Long, UserRepository> {
    @Bind
    private TextField firstName;
    @Bind
    private TextField lastName;
    @Bind
    private TextField patronymic;
    @Bind
    private DatePicker birthday;

    public UserEditor(UserRepository repository) {
        super(repository, User.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        firstName = new TextField("Фамилия");
        firstName.setRequired(true);
        firstName.setAutofocus(true);
        firstName.setValueChangeMode(ValueChangeMode.LAZY);
        addValidator("firstName", new StringNullableValidator());

        lastName = new TextField("Имя");
        lastName.setRequired(true);
        lastName.setValueChangeMode(ValueChangeMode.LAZY);
        addValidator("lastName", new StringNullableValidator());

        patronymic = new TextField("Отчество");
        patronymic.setValueChangeMode(ValueChangeMode.LAZY);

        birthday = new DatePicker("День рождения");
        birthday.setRequired(true);
        addValidator("birthday",
                new DateValidator(LocalDate.of(1900, 1, 1), LocalDate.now(),
                        "дата рождения может быть от 01.01.1900 до " + LocalDate.now().format(
                                DateTimeFormatter.ofPattern("d.MM.y"))));

        contentPanel.add(firstName, lastName, patronymic, birthday);
    }

    @Override
    protected User createNewEntity() {
        return new User();
    }
}
