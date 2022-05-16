package ru.andreeva.library.servicelayer.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.servicelayer.dao.Book;

@Component
public class BookSpecificationFactoryImpl implements SpecificationFactory<Book> {
    @Override
    public Specification<Book> create(SearchCriteria criteria) {
        return new BookSpecification(criteria);
    }
}
