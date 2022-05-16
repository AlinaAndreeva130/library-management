package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.repository.BookRepository;
import ru.andreeva.library.service.specification.BookSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.BookEditor;
import ru.andreeva.library.ui.component.IssuanceWindow;
import ru.andreeva.library.ui.view.MainLayout;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Фонд библиотеки")
@Tag("book-view")
@JsModule("./view/entity/book-view.ts")
@UIScope
@SpringComponent
public class BookView extends BaseEntityView<Book, Long, BookRepository> {
    private final IssuanceWindow issuanceWindow;
    @Id("issue")
    private Button issueBtn;
    @Id("return")
    private Button returnBtn;

    public BookView(BookSpecificationFactoryImpl bookSpecificationFactory,
                    BookRepository bookRepository,
                    BookEditor editor,
                    IssuanceWindow issuanceWindow) {
        super(bookRepository, bookSpecificationFactory, editor);
        this.issuanceWindow = issuanceWindow;
    }

    @Override
    protected void configureActionPanel() {
        issueBtn.addClickListener(event -> issuanceWindow.issueBook(grid.getSelectedItems().iterator().next()));
        issueBtn.setEnabled(false);

        returnBtn.addClickListener(event -> {
        });
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
        grid.addColumn(Book::getName).setHeader("Название").setKey("name");
        grid.addColumn(Book::getAuthor).setHeader("Автор").setKey("author");
        grid.addColumn(book -> book.getGenre().getName()).setHeader("Жанр").setKey("genre");
        grid.addColumn(Book::getYear).setHeader("Год издания").setKey("year");
        grid.addColumn(Book::getPageCount).setHeader("Количество страниц").setKey("pageCount");
        grid.addColumn(Book::getAgeRestriction).setHeader("Возрастное ограничение (с лет)").setKey("ageRestriction");
    }
}
