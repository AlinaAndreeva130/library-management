package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.repository.BookRepository;

@SpringComponent
@UIScope
public class BookEditor extends BaseEditor<Book, Long, BookRepository> {
    @Bind
    private TextField name;

    public BookEditor(BookRepository repository) {
        super(repository, Book.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField();
        name.setAutofocus(true);
        contentPanel.add(name);
    }

    @Override
    protected Book createNewEntity() {
        return new Book();
    }
}
