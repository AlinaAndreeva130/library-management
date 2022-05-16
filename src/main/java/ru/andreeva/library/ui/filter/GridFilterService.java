package ru.andreeva.library.ui.filter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.servicelayer.specification.SearchCriteria;
import ru.andreeva.library.servicelayer.specification.SpecificationFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GridFilterService<T, ID, R extends JpaRepository<T, ID> & JpaSpecificationExecutor<T>> {
    private final R repository;
    private final Grid<T> grid;
    private final Map<String, Specification<T>> filterMap;
    private final SpecificationFactory<T> specificationFactory;

    public GridFilterService(R repository, Grid<T> grid, SpecificationFactory<T> specificationFactory) {
        this.repository = repository;
        this.grid = grid;
        this.specificationFactory = specificationFactory;
        filterMap = new HashMap<>();
        fill();
    }

    public GridFilterService(R repository, Grid<T> grid, SpecificationFactory<T> specificationFactory,
                             Map<String, String> filters) {
        this(repository, grid, specificationFactory);
        filters.forEach(this::addCustomFilter);
        fill();
    }

    public void addCustomFilter(String filterKey, String filterValue) {
        addCustomFilter(filterKey, createSpecification(filterKey, filterValue));
    }

    public void addCustomFilter(String filterKey, Specification<T> specification) {
        filterMap.put(filterKey, specification);
    }

    public void addGridTextFilter(Grid.Column<T> column, HeaderRow filterRow, String header) {
        TextField filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setValueChangeTimeout(150);
        filter.addValueChangeListener(e -> filter(column.getKey(), e.getValue().trim()));
        // TODO: добавить перед заголовком колонки
        filter.setPlaceholder(header);
        filterRow.getCell(column).setComponent(filter);
    }

    public void refresh() {
        fill();
    }

    private void fill() {
        if (filterMap.isEmpty()) {
            fillGrid(fetch(null, null, null));
            return;
        }

        filterMap.forEach(this::filter);
    }

    private void filter(String key, String value) {
        filter(key, createSpecification(key, value));
    }

    private void filter(String key, Specification<T> specification) {
        Specification<T> predicate = Specification.where(specification);
        fillGrid(fetch(key, specification, predicate));
    }

    private List<T> fetch(String key, Specification<T> specification, Specification<T> predicate) {
        if (StringUtils.isNotEmpty(key) && specification != null && predicate != null) {
            filterMap.put(key, specification);

            for (Specification<T> spec : filterMap.values()) {
                if (specification.equals(spec)) {
                    continue;
                }
                predicate = predicate.and(spec);
            }
            return repository.findAll(predicate);
        }

        return repository.findAll();
    }

    private void fillGrid(List<T> items) {
        grid.setItems(items);
    }

    private Specification<T> createSpecification(String filterKey, String filterValue) {
        return specificationFactory.create(SearchCriteria.builder().key(filterKey).operation(":").value(filterValue)
                .build());
    }

}