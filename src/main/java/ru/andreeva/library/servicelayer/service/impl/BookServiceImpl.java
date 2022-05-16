package ru.andreeva.library.servicelayer.service.impl;

import org.springframework.stereotype.Service;
import ru.andreeva.library.servicelayer.dao.Book;
import ru.andreeva.library.servicelayer.dao.BookSerialNumber;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBook;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookId;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookLog;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.BookRepository;
import ru.andreeva.library.servicelayer.repository.BookSerialNumberRepository;
import ru.andreeva.library.servicelayer.repository.IssuanceOfBookLogRepository;
import ru.andreeva.library.servicelayer.repository.IssuanceOfBookRepository;
import ru.andreeva.library.servicelayer.service.BookService;
import ru.andreeva.library.servicelayer.util.Operation;
import ru.andreeva.library.servicelayer.util.Status;

import java.time.LocalDate;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookSerialNumberRepository bookSerialNumberRepository;
    private final IssuanceOfBookRepository issuanceOfBookRepository;
    private final IssuanceOfBookLogRepository issuanceOfBookLogRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           BookSerialNumberRepository bookSerialNumberRepository,
                           IssuanceOfBookRepository issuanceOfBookRepository,
                           IssuanceOfBookLogRepository issuanceOfBookLogRepository) {
        this.bookRepository = bookRepository;
        this.bookSerialNumberRepository = bookSerialNumberRepository;
        this.issuanceOfBookRepository = issuanceOfBookRepository;
        this.issuanceOfBookLogRepository = issuanceOfBookLogRepository;
    }

    @Override
    public void returnBook(Book book, BookSerialNumber bookSerialNumber, Reader reader) {
        issuanceOfBookRepository.delete(
                issuanceOfBookRepository.findByBookAndReaderAndSerialNumber(book, reader, bookSerialNumber));

        bookSerialNumber.setStatus(Status.FREE);
        bookSerialNumber.setStatusDate(LocalDate.now());
        bookSerialNumberRepository.save(bookSerialNumber);

        issuanceOfBookLogRepository.save(IssuanceOfBookLog.builder()
                .book(book)
                .bookSerialNumber(bookSerialNumber)
                .reader(reader)
                .operation(Operation.REFUND)
                .date(LocalDate.now())
                .build());
    }

    @Override
    public void issueBook(Book book, BookSerialNumber bookSerialNumber, Reader reader) {
        issuanceOfBookRepository.save(IssuanceOfBook.builder()
                .id(IssuanceOfBookId.builder().book(book).bookSerialNumber(bookSerialNumber).reader(reader).build())
                .operation(Operation.ISSUED)
                .date(LocalDate.now())
                .build());

        bookSerialNumber.setStatus(Status.BUSY);
        bookSerialNumber.setStatusDate(LocalDate.now());
        bookSerialNumberRepository.save(bookSerialNumber);

        issuanceOfBookLogRepository.save(IssuanceOfBookLog.builder()
                .book(book)
                .bookSerialNumber(bookSerialNumber)
                .reader(reader)
                .operation(Operation.ISSUED)
                .date(LocalDate.now())
                .build());
    }
}
