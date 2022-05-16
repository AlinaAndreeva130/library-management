package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.andreeva.library.service.dao.BookSerialNumber;
import ru.andreeva.library.service.dao.Reader;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long>, JpaSpecificationExecutor<Reader> {
    @Query("SELECT r FROM Reader r JOIN IssuanceOfBook i ON r = i.id.reader WHERE i.id.bookSerialNumber IN ?1")
    List<Reader> getDebtorsBySerialNumbers(List<BookSerialNumber> serialNumberIds);
}