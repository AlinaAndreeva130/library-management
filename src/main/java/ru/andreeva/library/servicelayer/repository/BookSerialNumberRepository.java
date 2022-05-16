package ru.andreeva.library.servicelayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.andreeva.library.servicelayer.dao.Book;
import ru.andreeva.library.servicelayer.dao.BookSerialNumber;

import java.util.List;

public interface BookSerialNumberRepository extends JpaRepository<BookSerialNumber, Long>, JpaSpecificationExecutor<BookSerialNumber> {
    @Query("SELECT b FROM BookSerialNumber b WHERE b.book = ?1")
    List<BookSerialNumber> findByBook(Book book);
}