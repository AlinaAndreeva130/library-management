package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.Book;
import ru.andreeva.library.servicelayer.dao.BookSerialNumber;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.ReaderRepository;
import ru.andreeva.library.servicelayer.service.BookService;
import ru.andreeva.library.servicelayer.util.Status;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@SpringComponent
@UIScope
public class IssuanceBookWindow extends Dialog {
    private final ReaderRepository readerRepository;
    private final BookService bookService;
    private final ComboBox<BookSerialNumber> serialNumber;
    private final ComboBox<Reader> reader;


    private Book currentBook;

    public IssuanceBookWindow(ReaderRepository readerRepository, BookService bookService) {
        this.readerRepository = readerRepository;
        this.bookService = bookService;

        serialNumber = new ComboBox<>("Серийный номер (свободный)");
        serialNumber.setWidthFull();
        serialNumber.setRequired(true);
        serialNumber.setItemLabelGenerator(BookSerialNumber::getSerialNumber);
        serialNumber.addValueChangeListener(event -> validateSerialNumber(event.getValue()));

        reader = new ComboBox<>("Читатель");
        reader.setWidthFull();
        reader.setRequired(true);
        reader.setItemLabelGenerator(
                item -> item.getFirstName() + " " + item.getLastName() + " " + item.getPatronymic() + " " +
                        item.getClazz() + " класс " +
                        item.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y")));
        reader.addValueChangeListener(event -> validateReader(event.getValue()));

        Button issue = new Button("Выдать");
        issue.addClickListener(this::doIssue);

        Button cancel = new Button("Отмена");
        cancel.addClickListener(event -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(issue, cancel);
        horizontalLayout.setWidthFull();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(new VerticalLayout(serialNumber, reader), horizontalLayout);
        setWidth("600px");
    }

    private boolean validateSerialNumber(BookSerialNumber value) {
        if (value == null) {
            serialNumber.setErrorMessage("обязательно к заполнению");
            return false;
        } else {
            serialNumber.setErrorMessage("");
            return true;
        }
    }

    private boolean validateReader(Reader value) {
        boolean isValid = true;
        String errorMessage = "";

        if (value == null) {
            errorMessage = "обязательно к заполнению";
            isValid = false;
        } else {
            if (Period.between(value.getBirthday(), LocalDate.now()).getYears() < currentBook.getAgeRestriction()) {
                Notification.show("данная книга разрешена с " + currentBook.getAgeRestriction() + " лет", 3000,
                        Notification.Position.BOTTOM_START);
                isValid = false;
            }
        }

        reader.setErrorMessage(errorMessage);
        return isValid;
    }

    public void issueBook(Book book) {
        currentBook = book;
        serialNumber.clear();
        serialNumber.setItems(book.getBookSerialNumbers().stream().filter(item -> item.getStatus() == Status.FREE));
        reader.clear();
        reader.setItems(readerRepository.findAll());
        open();
    }

    private void doIssue(ClickEvent<Button> event) {
        BookSerialNumber bookSerialNumber = serialNumber.getValue();
        Reader reader = this.reader.getValue();
        boolean isValidSerialNumber = validateSerialNumber(bookSerialNumber);
        boolean isValidReader = validateReader(reader);
        if (!isValidSerialNumber || !isValidReader) {
            Notification.show("Некорректно заполнены поля", 3000, Notification.Position.TOP_STRETCH);
            return;
        }

        close();
        bookService.issueBook(currentBook, bookSerialNumber, reader);
    }
}
