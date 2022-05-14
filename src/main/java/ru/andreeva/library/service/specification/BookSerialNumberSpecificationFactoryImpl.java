package ru.andreeva.library.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.service.dao.BookSerialNumber;

@Component
public class BookSerialNumberSpecificationFactoryImpl implements SpecificationFactory<BookSerialNumber> {
    @Override
    public Specification<BookSerialNumber> create(SearchCriteria criteria) {
        return new BookSerialNumberSpecification(criteria);
    }
}
