package ru.andreeva.library.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.service.dao.Reader;

@Component
public class ReaderSpecificationFactoryImpl implements SpecificationFactory<Reader> {
    @Override
    public Specification<Reader> create(SearchCriteria criteria) {
        return new ReaderSpecification(criteria);
    }
}
