package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.dao.Book;

public interface BooksRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
}
