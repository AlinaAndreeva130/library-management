package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.Book;
import ru.andreeva.library.servicelayer.repository.BookRepository;
import ru.andreeva.library.servicelayer.specification.BookSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.BookEditor;
import ru.andreeva.library.ui.component.IssuanceBookWindow;
import ru.andreeva.library.ui.component.ReturnBookWindow;
import ru.andreeva.library.ui.view.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Фонд библиотеки")
@Tag("book-view")
@JsModule("./view/entity/book-view.ts")
@UIScope
@SpringComponent
public class BookView extends BaseEntityView<Book, Long, BookRepository> {
    private final IssuanceBookWindow issuanceBookWindow;
    private final ReturnBookWindow returnBookWindow;
    @Id("issue")
    private Button issueBtn;
    @Id("return")
    private Button returnBtn;

    public BookView(BookSpecificationFactoryImpl bookSpecificationFactory,
                    BookRepository bookRepository,
                    BookEditor editor,
                    IssuanceBookWindow issuanceBookWindow,
                    ReturnBookWindow returnBookWindow) {
        super(bookRepository, bookSpecificationFactory, editor);
        this.issuanceBookWindow = issuanceBookWindow;
        this.returnBookWindow = returnBookWindow;
    }

    @Override
    protected void configureActionPanel() {
        issueBtn.addClickListener(event -> issuanceBookWindow.issueBook(grid.getSelectedItems().iterator().next()));
        issueBtn.setEnabled(false);

        returnBtn.addClickListener(event -> returnBookWindow.returnBook(grid.getSelectedItems().iterator().next()));
        returnBtn.setEnabled(false);

        super.configureActionPanel();
    }

    @Override
    protected boolean refreshActionPanel() {
        boolean isSelected = super.refreshActionPanel();
        issueBtn.setEnabled(isSelected);
        returnBtn.setEnabled(isSelected);
        return isSelected;
    }

    @Override
    protected void initAfterConstructionObject() {

    }

    @Override
    protected void createColumns() {
        HeaderRow headerRow = grid.appendHeaderRow();
        filterService.addGridTextFilter(grid.addColumn(Book::getName)
                .setHeader("Название")
                .setSortable(true)
                .setResizable(true)
                .setAutoWidth(true)
                .setKey("name"), headerRow, "Название");
        filterService.addGridTextFilter(
                grid.addColumn(Book::getAuthor).setHeader("Автор").setSortable(true).setKey("author"), headerRow,
                "Автор");
        grid.addColumn(book -> book.getGenre().getName())
                .setHeader("Жанр")
                .setSortable(true)
                .setAutoWidth(true)
                .setKey("genre");
        grid.addColumn(Book::getYear).setHeader("Год издания").setAutoWidth(true).setKey("year");
        grid.addColumn(Book::getPageCount).setHeader("Количество страниц").setAutoWidth(true).setKey("pageCount");
        grid.addColumn(Book::getAgeRestriction)
                .setHeader("Возрастное ограничение (с лет)")
                .setSortable(true)
                .setResizable(true)
                .setKey("ageRestriction");
    }
}
