package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ru.andreeva.library.service.dao.User;
import ru.andreeva.library.service.repository.BooksRepository;

@SpringComponent
@UIScope
public class UserEditor extends BaseEditor<User> {
    @Bind("firstName")
    private TextField name;

    public UserEditor(BooksRepository repository) {
        super(repository, User.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField();
        contentPanel.add(name);
    }

    @Override
    protected User createNewEntity() {
        return new User();
    }
}
