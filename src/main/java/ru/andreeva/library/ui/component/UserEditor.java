package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.User;
import ru.andreeva.library.service.repository.UsersRepository;

@SpringComponent
@UIScope
public class UserEditor extends BaseEditor<User, Integer, UsersRepository> {
    @Bind
    private TextField firstName;
    @Bind
    private TextField lastName;
    @Bind
    private TextField patronymic;
    @Bind
    private TextField address;

    public UserEditor(UsersRepository repository) {
        super(repository, User.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        firstName = new TextField("Фамилия");
        firstName.setAutofocus(true);
        lastName = new TextField("Имя");
        patronymic = new TextField("Отчество");
        address = new TextField("Адрес");
        contentPanel.add(firstName, lastName, patronymic, address);
    }

    @Override
    protected User createNewEntity() {
        return new User();
    }
}
