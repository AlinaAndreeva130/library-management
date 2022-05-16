package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.IssuanceOfBook;
import ru.andreeva.library.service.dao.IssuanceOfBookId;
import ru.andreeva.library.service.dao.Reader;
import ru.andreeva.library.service.repository.IssuanceOfBookRepository;
import ru.andreeva.library.service.specification.IssuanceOfBookSpecificationFactoryImpl;
import ru.andreeva.library.ui.view.MainLayout;

import java.time.format.DateTimeFormatter;

@Route(value = "issue-of-book", layout = MainLayout.class)
@PageTitle("Выданные книги")
@Tag("issue-of-book-view")
@JsModule("./view/entity/issue-of-book-view.ts")
@UIScope
@SpringComponent
public class IssueOfBookView extends BaseEntityView<IssuanceOfBook, IssuanceOfBookId, IssuanceOfBookRepository> {

    public IssueOfBookView(IssuanceOfBookSpecificationFactoryImpl specificationFactory,
                           IssuanceOfBookRepository repository) {
        super(repository, specificationFactory, null);
    }

    @Override
    protected void createColumns() {
        grid.addColumn(item -> item.getId().getBook().getName())
                .setHeader("Название книги")
                .setSortable(true)
                .setKey("bookName");
        grid.addColumn(item -> item.getId().getBookSerialNumber().getSerialNumber())
                .setHeader("Серийный номер книги")
                .setKey("serialNumber");
        grid.addColumn(item -> {
            Reader reader = item.getId().getReader();
            return reader.getFirstName() + " " + reader.getLastName() + " " + reader.getPatronymic() + " " +
                    reader.getClazz() + " класс " + reader.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y"));
        }).setHeader("Читатель").setSortable(true).setAutoWidth(true).setKey("reader");
        grid.addColumn(item -> item.getDate().format(DateTimeFormatter.ofPattern("dd.MM.y")))
                .setHeader("Дата операции").setSortable(true)
                .setKey("operationDate");
    }
}
