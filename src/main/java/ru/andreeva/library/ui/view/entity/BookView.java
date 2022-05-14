package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.repository.BooksRepository;
import ru.andreeva.library.service.specification.BookSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.BookEditor;
import ru.andreeva.library.ui.view.MainLayout;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Фонд библиотеки")
@Tag("book-view")
@JsModule("./view/entity/book-view.ts")
@UIScope
@SpringComponent
public class BookView extends BaseEntityView<Book, Integer, BooksRepository> {

    public BookView(BookSpecificationFactoryImpl bookSpecificationFactory, BooksRepository booksRepository,
                    BookEditor editor) {
        super(booksRepository, bookSpecificationFactory, editor);
    }

    @Override
    protected void createColumns() {
        HeaderRow filterRow = grid.prependHeaderRow();
        grid.addColumn(Book::getName).setHeader("Название").setKey("name");
        grid.addColumn(Book::getAuthor).setHeader("Автор").setKey("author");
        filterService.addGridTextFilter(grid.addColumn(Book::getGenre).setHeader("Жанр").setKey("genre"), filterRow);
        filterService.addGridTextFilter(grid.addColumn(Book::getYear).setHeader("Год издания").setKey("year"),
                filterRow);
        filterService.addGridTextFilter(
                grid.addColumn(Book::getPageCount).setHeader("Количество страниц").setKey("pageCount"), filterRow);
    }
}
