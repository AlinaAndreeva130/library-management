package ru.andreeva.library.service.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.andreeva.library.service.dao.Genre;

@Component
public class GenreSpecificationFactoryImpl implements SpecificationFactory<Genre> {
    @Override
    public Specification<Genre> create(SearchCriteria criteria) {
        return new GenreSpecification(criteria);
    }
}
