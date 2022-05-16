package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBook;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookId;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.IssuanceOfBookRepository;
import ru.andreeva.library.servicelayer.service.BookService;
import ru.andreeva.library.servicelayer.specification.IssuanceOfBookSpecificationFactoryImpl;
import ru.andreeva.library.ui.view.MainLayout;

import java.time.format.DateTimeFormatter;

@Route(value = "issue-of-book", layout = MainLayout.class)
@PageTitle("Выданные книги")
@Tag("issue-of-book-view")
@JsModule("./view/entity/issue-of-book-view.ts")
@UIScope
@SpringComponent
public class IssueOfBookView extends BaseEntityView<IssuanceOfBook, IssuanceOfBookId, IssuanceOfBookRepository> {
    private final BookService bookService;
    @Id("return")
    private Button returnBtn;

    public IssueOfBookView(IssuanceOfBookSpecificationFactoryImpl specificationFactory,
                           IssuanceOfBookRepository repository,
                           BookService bookService) {
        super(repository, specificationFactory, null);
        this.bookService = bookService;
    }

    @Override
    protected void configureActionPanel() {
        returnBtn.addClickListener(event -> {
            IssuanceOfBook issuanceOfBook = grid.getSelectedItems().iterator().next();
            bookService.returnBook(issuanceOfBook.getId().getBook(), issuanceOfBook.getId().getBookSerialNumber(),
                    issuanceOfBook.getId().getReader());
            filterService.refresh();
            grid.getDataProvider().refreshAll();
        });
        returnBtn.setEnabled(false);

        super.configureActionPanel();
    }

    @Override
    protected boolean refreshActionPanel() {
        boolean isSelected = super.refreshActionPanel();
        returnBtn.setEnabled(isSelected);
        return isSelected;
    }

    @Override
    protected void createColumns() {
        grid.addColumn(item -> item.getId().getBook().getName())
                .setHeader("Название книги")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true)
                .setKey("bookName");
        grid.addColumn(item -> item.getId().getBookSerialNumber().getSerialNumber())
                .setHeader("Серийный номер книги")
                .setAutoWidth(true)
                .setResizable(true)
                .setKey("serialNumber");
        grid.addColumn(item -> {
            Reader reader = item.getId().getReader();
            return reader.getFirstName() + " " + reader.getLastName() + " " + reader.getPatronymic() + " " +
                    reader.getClazz() + " класс " + reader.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y"));
        }).setHeader("Читатель").setSortable(true).setResizable(true).setAutoWidth(true).setKey("reader");
        grid.addColumn(item -> item.getDate().format(DateTimeFormatter.ofPattern("dd.MM.y")))
                .setHeader("Дата операции")
                .setSortable(true)
                .setKey("operationDate");
    }
}
