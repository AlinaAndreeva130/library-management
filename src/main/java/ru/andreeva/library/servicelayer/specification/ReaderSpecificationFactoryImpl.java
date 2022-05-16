package ru.andreeva.library.servicelayer.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.servicelayer.dao.Reader;

@Component
public class ReaderSpecificationFactoryImpl implements SpecificationFactory<Reader> {
    @Override
    public Specification<Reader> create(SearchCriteria criteria) {
        return new ReaderSpecification(criteria);
    }
}
