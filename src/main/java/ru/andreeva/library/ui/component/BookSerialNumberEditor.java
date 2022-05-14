package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.dao.BookSerialNumber;
import ru.andreeva.library.service.repository.BookSerialNumberRepository;

@SpringComponent
@UIScope
public class BookSerialNumberEditor extends BaseEditor<BookSerialNumber, Long, BookSerialNumberRepository> {
    @Bind
    private ComboBox<Book> book;
    @Bind
    private String serialNumber;

    public BookSerialNumberEditor(BookSerialNumberRepository repository) {
        super(repository, BookSerialNumber.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        book = new ComboBox<>("Книга");
        book.setAutofocus(true);
        contentPanel.add(book);
    }

    @Override
    protected BookSerialNumber createNewEntity() {
        return new BookSerialNumber();
    }
}
