package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.dao.BookSerialNumber;

public interface BookSerialNumberRepository extends JpaRepository<BookSerialNumber, Long>, JpaSpecificationExecutor<BookSerialNumber> {
}