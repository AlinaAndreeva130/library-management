package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.User;
import ru.andreeva.library.service.repository.UserRepository;
import ru.andreeva.library.service.specification.UserSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.UserEditor;
import ru.andreeva.library.ui.view.MainLayout;

import java.time.format.DateTimeFormatter;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Администраторы")
@Tag("user-view")
@JsModule("./view/entity/user-view.ts")
@UIScope
@SpringComponent
public class UserView extends BaseEntityView<User, Long, UserRepository> {

    public UserView(UserSpecificationFactoryImpl specificationFactory, UserRepository repository,
                    UserEditor editor) {
        super(repository, specificationFactory, editor);
    }

    @Override
    protected void createColumns() {
        grid.addColumn(User::getFirstName).setHeader("Фамилия").setSortable(true).setKey("firstName");
        grid.addColumn(User::getLastName).setHeader("Имя").setSortable(true).setKey("lastName");
        grid.addColumn(User::getPatronymic).setHeader("Отчество").setSortable(true).setKey("patronymic");
        grid.addColumn(user -> user.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y")))
                .setHeader("Дата рождения").setSortable(true)
                .setKey("birthday");
    }
}
