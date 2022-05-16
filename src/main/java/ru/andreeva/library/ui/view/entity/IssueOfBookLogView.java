package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookLog;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.IssuanceOfBookLogRepository;
import ru.andreeva.library.servicelayer.specification.IssuanceOfBookLogSpecificationFactoryImpl;
import ru.andreeva.library.ui.view.MainLayout;

import java.time.format.DateTimeFormatter;

@Route(value = "log-issue-of-book", layout = MainLayout.class)
@PageTitle("Журнал выдачи и возврата книг")
@Tag("issue-of-book-log-view")
@JsModule("./view/entity/issue-of-book-log-view.ts")
@UIScope
@SpringComponent
public class IssueOfBookLogView extends BaseEntityView<IssuanceOfBookLog, Long, IssuanceOfBookLogRepository> {

    public IssueOfBookLogView(IssuanceOfBookLogSpecificationFactoryImpl specificationFactory,
                              IssuanceOfBookLogRepository repository) {
        super(repository, specificationFactory, null);
    }

    @Override
    protected void createColumns() {
        grid.addColumn(item -> item.getBook().getName())
                .setHeader("Название книги")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true)
                .setKey("bookName");
        grid.addColumn(item -> item.getBookSerialNumber().getSerialNumber())
                .setHeader("Серийный номер книги")
                .setKey("serialNumber");
        grid.addColumn(item -> {
            Reader reader = item.getReader();
            return reader.getFirstName() + " " + reader.getLastName() + " " + reader.getPatronymic() + " " +
                    reader.getClazz() + " класс " + reader.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y"));
        }).setHeader("Читатель").setSortable(true).setAutoWidth(true).setKey("reader");
        grid.addColumn(item -> item.getOperation().getName())
                .setHeader("Операция")
                .setSortable(true)
                .setAutoWidth(true)
                .setKey("operation");
        grid.addColumn(item -> item.getDate().format(DateTimeFormatter.ofPattern("dd.MM.y")))
                .setHeader("Дата операции")
                .setSortable(true)
                .setKey("operationDate");
    }
}
