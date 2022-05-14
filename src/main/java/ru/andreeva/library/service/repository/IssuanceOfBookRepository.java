package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.dao.IssuanceOfBook;
import ru.andreeva.library.service.dao.IssuanceOfBookId;

public interface IssuanceOfBookRepository extends JpaRepository<IssuanceOfBook, IssuanceOfBookId>, JpaSpecificationExecutor<IssuanceOfBook> {
}