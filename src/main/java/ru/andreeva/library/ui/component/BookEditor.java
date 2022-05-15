package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.repository.BookRepository;
import ru.andreeva.library.service.util.EnumNullableValidator;
import ru.andreeva.library.service.util.Genre;
import ru.andreeva.library.service.util.StringNullableValidator;

@SpringComponent
@UIScope
public class BookEditor extends BaseEditor<Book, Long, BookRepository> {
    @Bind
    private TextField name;
    @Bind
    private TextField author;
    @Bind
    private ComboBox<Genre> genre;
    @Bind
    private TextField publisher;
    @Bind(converter = Bind.Converter.STRING_TO_INTEGER)
    private TextField year;
    @Bind(converter = Bind.Converter.STRING_TO_INTEGER)
    private TextField pageCount;
    @Bind(converter = Bind.Converter.STRING_TO_INTEGER)
    private TextField ageRestriction;


    public BookEditor(BookRepository repository) {
        super(repository, Book.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField("Название");
        name.setRequired(true);
        addValidator("name", new StringNullableValidator());
        name.setAutofocus(true);
        name.setValueChangeMode(ValueChangeMode.LAZY);

        author = new TextField("Автор");
        author.setRequired(true);
        author.setValueChangeMode(ValueChangeMode.LAZY);
        addValidator("author", new StringNullableValidator());

        genre = new ComboBox<>("Жанр");
        genre.setItemLabelGenerator(Genre::getName);
        genre.setItems(Genre.values());
        genre.setRequired(true);
        addValidator("genre", new EnumNullableValidator<>(Genre.class));

        publisher = new TextField("Издательство");

        year = new TextField("Год издания");
        year.setRequired(true);
        addValidator("year", new IntegerRangeValidator("должно быть больше нуля", 0, Integer.MAX_VALUE));

        pageCount = new TextField("Количество страниц");

        ageRestriction = new TextField("Возрастное ограничение (разрешено с )");
        ageRestriction.setRequired(true);
        addValidator("ageRestriction", new IntegerRangeValidator("должно быть от нуля до 100", 0, 100));

        contentPanel.add(name, author, genre, publisher, year, pageCount, ageRestriction);
    }


    @Override
    protected Book createNewEntity() {
        return new Book();
    }
}
