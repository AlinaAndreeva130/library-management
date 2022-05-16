package ru.andreeva.library.servicelayer.service;

import ru.andreeva.library.servicelayer.dao.Book;
import ru.andreeva.library.servicelayer.dao.BookSerialNumber;
import ru.andreeva.library.servicelayer.dao.Reader;

public interface BookService {
    void returnBook(Book book, BookSerialNumber bookSerialNumber, Reader reader);

    void issueBook(Book book, BookSerialNumber bookSerialNumber, Reader reader);
}
