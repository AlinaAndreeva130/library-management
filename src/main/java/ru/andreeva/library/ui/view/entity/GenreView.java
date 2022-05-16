package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.Genre;
import ru.andreeva.library.servicelayer.repository.GenreRepository;
import ru.andreeva.library.servicelayer.specification.GenreSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.GenreEditor;
import ru.andreeva.library.ui.view.MainLayout;

@Route(value = "genres", layout = MainLayout.class)
@PageTitle("Жанры")
@Tag("genre-view")
@JsModule("./view/entity/genre-view.ts")
@UIScope
@SpringComponent
public class GenreView extends BaseEntityView<Genre, Long, GenreRepository> {

    public GenreView(GenreSpecificationFactoryImpl specificationFactory,
                     GenreRepository repository,
                     GenreEditor editor) {
        super(repository, specificationFactory, editor);
    }

    @Override
    protected void createColumns() {
        grid.addColumn(Genre::getName).setSortable(true).setHeader("Название жанра").setKey("name");
    }
}
