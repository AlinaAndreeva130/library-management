package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.dao.BookSerialNumber;
import ru.andreeva.library.service.dao.IssuanceOfBook;
import ru.andreeva.library.service.dao.IssuanceOfBookId;
import ru.andreeva.library.service.dao.Reader;

import java.util.List;

public interface IssuanceOfBookRepository extends JpaRepository<IssuanceOfBook, IssuanceOfBookId>, JpaSpecificationExecutor<IssuanceOfBook> {
    @Query("SELECT i FROM IssuanceOfBook i WHERE i.id.book = ?1 AND i.id.reader = ?2 AND i.id.bookSerialNumber = ?3")
    IssuanceOfBook findByBookAndReaderAndSerialNumber(Book currentBook,
                                                      Reader reader,
                                                      BookSerialNumber bookSerialNumber);

    @Query("SELECT i.id.bookSerialNumber FROM IssuanceOfBook i WHERE i.id.book = ?1 AND i.id.reader = ?2 AND i.id.bookSerialNumber.status = 'BUSY'")
    List<BookSerialNumber> getBusySerialNumbersByBookAndReader(Book currentBook, Reader readerValue);
}