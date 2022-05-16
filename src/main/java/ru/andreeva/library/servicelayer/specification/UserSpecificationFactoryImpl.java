package ru.andreeva.library.servicelayer.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.servicelayer.dao.User;

@Component
public class UserSpecificationFactoryImpl implements SpecificationFactory<User> {
    @Override
    public Specification<User> create(SearchCriteria criteria) {
        return new UserSpecification(criteria);
    }
}
