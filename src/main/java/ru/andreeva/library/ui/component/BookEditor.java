package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.dao.BookSerialNumber;
import ru.andreeva.library.service.dao.Genre;
import ru.andreeva.library.service.repository.BookRepository;
import ru.andreeva.library.service.repository.BookSerialNumberRepository;
import ru.andreeva.library.service.repository.GenreRepository;
import ru.andreeva.library.service.util.Status;
import ru.andreeva.library.ui.util.EnumNullableValidator;
import ru.andreeva.library.ui.util.IntegerValidator;
import ru.andreeva.library.ui.util.StringNullableValidator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private TextArea serialNumbers;
    private final BookSerialNumberRepository bookSerialNumberRepository;
    private final GenreRepository genreRepository;


    public BookEditor(BookRepository repository, BookSerialNumberRepository bookSerialNumberRepository,
                      GenreRepository genreRepository) {
        super(repository, Book.class);
        this.bookSerialNumberRepository = bookSerialNumberRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField("Название");
        name.setRequired(true);
        name.setAutofocus(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setWidthFull();
        addValidator("name", new StringNullableValidator());

        author = new TextField("Автор");
        author.setRequired(true);
        author.setValueChangeMode(ValueChangeMode.EAGER);
        author.setWidthFull();
        addValidator("author", new StringNullableValidator());

        genre = new ComboBox<>("Жанр");
        genre.setItemLabelGenerator(Genre::getName);
        genre.setRequired(true);
        genre.setWidthFull();
        addValidator("genre", new EnumNullableValidator<>(Genre.class));

        publisher = new TextField("Издательство");
        publisher.setWidthFull();

        year = new TextField("Год издания");
        year.setRequired(true);
        year.setWidthFull();
        year.setValueChangeMode(ValueChangeMode.EAGER);
        addValidator("year", new IntegerValidator(1, Integer.MAX_VALUE, "должно быть больше нуля"));

        pageCount = new TextField("Количество страниц");
        pageCount.setWidthFull();

        ageRestriction = new TextField("Возрастное ограничение (разрешено с )");
        ageRestriction.setRequired(true);
        ageRestriction.setWidthFull();
        ageRestriction.setValueChangeMode(ValueChangeMode.EAGER);
        addValidator("ageRestriction", new IntegerValidator(1, 100, "должно быть от 1 до 100"));

        serialNumbers = new TextArea("Серийные номера (через зяпятую)");
        serialNumbers.setWidthFull();

        contentPanel.add(name, author, genre, publisher, year, pageCount, ageRestriction, serialNumbers);
        contentPanel.setWidth("350px");
    }

    @Override
    public void editEntity(Book entity, Runnable actionAfterEdit) {
        super.editEntity(entity, actionAfterEdit);
        serialNumbers.setValue(entity.getBookSerialNumbers()
                .stream()
                .map(BookSerialNumber::getSerialNumber)
                .collect(Collectors.joining(", ")));
    }

    @Override
    protected Book createNewEntity() {
        return new Book();
    }

    @Override
    protected void actionBeforeOpen() {
        super.actionBeforeOpen();
        serialNumbers.clear();
        genre.setItems(genreRepository.findAll());
    }

    @Override
    protected void actionAfterSave(Book book) {
        List<BookSerialNumber> bookSerialNumbers = bookSerialNumberRepository.findByBook(book);
        if (serialNumbers.getValue() == null) {
            try {
                bookSerialNumberRepository.deleteAll(bookSerialNumbers);
            } catch (Exception e) {
                Notification.show(
                        "Невозможно удалить все серийные номера, так как. на них завязаны операции выдачи книг," +
                                " или они используются в журнале истории выдачи книг",
                        3000, Notification.Position.TOP_STRETCH);
            }
        } else {
            List<String> serialNumbersList =
                    Arrays.stream(serialNumbers.getValue().trim().split(",")).collect(Collectors.toList());

            saveNewSerialNumbers(book, bookSerialNumbers, serialNumbersList);
            deleteNotUsingSerialNumbers(bookSerialNumbers, serialNumbersList);
        }
    }

    private void saveNewSerialNumbers(Book book,
                                      List<BookSerialNumber> bookSerialNumbers,
                                      List<String> serialNumbersList) {
        List<String> notSavedNumbers = serialNumbersList.stream()
                .filter(number -> bookSerialNumbers.stream()
                        .noneMatch(item -> item.getSerialNumber().equals(number.trim())))
                .collect(Collectors.toList());
        if (!notSavedNumbers.isEmpty()) {
            notSavedNumbers.stream()
                    .filter(StringUtils::isNotEmpty)
                    .forEach(item -> bookSerialNumberRepository.save(BookSerialNumber.builder()
                            .book(book)
                            .serialNumber(item.trim())
                            .creationDate(LocalDate.now())
                            .status(Status.FREE)
                            .statusDate(LocalDate.now()).build()));
        }
    }

    private void deleteNotUsingSerialNumbers(List<BookSerialNumber> bookSerialNumbers, List<String> serialNumbersList) {
        List<BookSerialNumber> deletingNumbers = bookSerialNumbers.stream()
                .filter(number -> serialNumbersList.stream()
                        .noneMatch(item -> item.trim().equals(number.getSerialNumber())))
                .collect(
                        Collectors.toList());
        if (!deletingNumbers.isEmpty()) {
            deletingNumbers.forEach(number -> {
                try {
                    bookSerialNumberRepository.delete(number);
                } catch (Exception e) {
                    Notification.show(
                            "Невозможно удалить серийный номер \"" + number.getSerialNumber() + "\", так как на" +
                                    " него завязана операция выдачи книг, или он используется в журнале истории " +
                                    "выдачи книг",
                            2000, Notification.Position.BOTTOM_START);
                }
            });
        }
    }


    @Override
    public void deleteEntity(Book entity, Runnable actionAfterDelete) {
        bookSerialNumberRepository.deleteAll(bookSerialNumberRepository.findByBook(entity));
        try {
            super.deleteEntity(entity, actionAfterDelete);
        } catch (DataIntegrityViolationException exception) {
            Notification.show(
                    "Невозможно удалить книгу, так как у неё есть серийные номера, которые используются в операции" +
                            " выдачи книг, или присутствуют в журнале истории выдачи книг");
        }
    }
}
