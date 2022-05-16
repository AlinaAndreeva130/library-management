package ru.andreeva.library.servicelayer.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBook;

@Component
public class IssuanceOfBookSpecificationFactoryImpl implements SpecificationFactory<IssuanceOfBook> {
    @Override
    public Specification<IssuanceOfBook> create(SearchCriteria criteria) {
        return new IssuanceOfBookSpecification(criteria);
    }
}
