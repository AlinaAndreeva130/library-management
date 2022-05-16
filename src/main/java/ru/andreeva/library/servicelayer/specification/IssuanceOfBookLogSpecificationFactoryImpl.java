package ru.andreeva.library.servicelayer.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookLog;

@Component
public class IssuanceOfBookLogSpecificationFactoryImpl implements SpecificationFactory<IssuanceOfBookLog> {
    @Override
    public Specification<IssuanceOfBookLog> create(SearchCriteria criteria) {
        return new IssuanceOfBookLogSpecification(criteria);
    }
}
