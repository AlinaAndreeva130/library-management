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
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.dao.BookSerialNumber;
import ru.andreeva.library.service.dao.IssuanceOfBookLog;
import ru.andreeva.library.service.dao.Reader;
import ru.andreeva.library.service.repository.BookSerialNumberRepository;
import ru.andreeva.library.service.repository.IssuanceOfBookLogRepository;
import ru.andreeva.library.service.repository.IssuanceOfBookRepository;
import ru.andreeva.library.service.repository.ReaderRepository;
import ru.andreeva.library.service.util.Operation;
import ru.andreeva.library.service.util.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class ReturnBookWindow extends Dialog {
    private final BookSerialNumberRepository bookSerialNumberRepository;
    private final IssuanceOfBookRepository issuanceOfBookRepository;
    private final IssuanceOfBookLogRepository issuanceOfBookLogRepository;
    private final ReaderRepository readerRepository;
    private final ComboBox<BookSerialNumber> serialNumber;
    private final ComboBox<Reader> reader;


    private Book currentBook;

    public ReturnBookWindow(BookSerialNumberRepository bookSerialNumberRepository,
                            IssuanceOfBookRepository issuanceOfBookRepository,
                            IssuanceOfBookLogRepository issuanceOfBookLogRepository,
                            ReaderRepository readerRepository) {
        this.bookSerialNumberRepository = bookSerialNumberRepository;
        this.issuanceOfBookRepository = issuanceOfBookRepository;
        this.issuanceOfBookLogRepository = issuanceOfBookLogRepository;
        this.readerRepository = readerRepository;

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
        reader.addValueChangeListener(event -> {
            Reader readerValue = event.getValue();
            if (validateReader(readerValue)) {
                serialNumber.setItems(
                        issuanceOfBookRepository.getBusySerialNumbersByBookAndReader(currentBook, readerValue));
            } else {
                serialNumber.clear();
            }
        });

        Button returnBtn = new Button("Вернуть");
        returnBtn.addClickListener(this::doReturn);

        Button cancel = new Button("Отмена");
        cancel.addClickListener(event -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(returnBtn, cancel);
        horizontalLayout.setWidthFull();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(new VerticalLayout(reader, serialNumber), horizontalLayout);
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
        if (value == null) {
            reader.setErrorMessage("обязательно к заполнению");
            return false;
        } else {
            reader.setErrorMessage("");
            return true;
        }
    }

    public void returnBook(Book book) {
        currentBook = book;
        reader.clear();
        reader.setItems(readerRepository.getDebtorsBySerialNumbers(book.getBookSerialNumbers()
                .stream()
                .filter(item -> item.getStatus() == Status.BUSY)
                .collect(Collectors.toList())));
        serialNumber.clear();
        open();
    }

    private void doReturn(ClickEvent<Button> event) {
        BookSerialNumber bookSerialNumber = serialNumber.getValue();
        Reader reader = this.reader.getValue();
        boolean isValidSerialNumber = validateSerialNumber(bookSerialNumber);
        boolean isValidReader = validateReader(reader);
        if (!isValidSerialNumber || !isValidReader) {
            Notification.show("Некорректно заполнены поля", 3000, Notification.Position.TOP_STRETCH);
            return;
        }

        close();

        issuanceOfBookRepository.delete(
                issuanceOfBookRepository.findByBookAndReaderAndSerialNumber(currentBook, reader, bookSerialNumber));

        bookSerialNumber.setStatus(Status.FREE);
        bookSerialNumber.setStatusDate(LocalDate.now());
        bookSerialNumberRepository.save(bookSerialNumber);

        issuanceOfBookLogRepository.save(IssuanceOfBookLog.builder()
                .book(currentBook)
                .bookSerialNumber(bookSerialNumber)
                .reader(reader)
                .operation(Operation.REFUND)
                .date(LocalDate.now())
                .build());
    }
}
