package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.repository.BooksRepository;

@SpringComponent
@UIScope
public class BookEditor extends BaseEditor<Book> {
    @Bind
    private TextField name;

    public BookEditor(BooksRepository repository) {
        super(repository, Book.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField();
        contentPanel.add(name);
    }

    @Override
    protected Book createNewEntity() {
        return new Book();
    }
}
