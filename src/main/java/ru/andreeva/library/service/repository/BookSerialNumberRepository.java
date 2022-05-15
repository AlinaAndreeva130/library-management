package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.andreeva.library.service.dao.Book;
import ru.andreeva.library.service.dao.BookSerialNumber;

import java.util.List;

public interface BookSerialNumberRepository extends JpaRepository<BookSerialNumber, Long>, JpaSpecificationExecutor<BookSerialNumber> {
    @Query("select b from BookSerialNumber b where b.book = ?1")
    List<BookSerialNumber> findByBook(Book book);
}