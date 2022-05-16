package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Genre;
import ru.andreeva.library.service.repository.GenreRepository;
import ru.andreeva.library.ui.util.StringNullableValidator;

@SpringComponent
@UIScope
public class GenreEditor extends BaseEditor<Genre, Long, GenreRepository> {
    @Bind
    private TextField name;

    public GenreEditor(GenreRepository repository) {
        super(repository, Genre.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        name = new TextField("Название");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setAutofocus(true);
        addValidator("name", new StringNullableValidator());
        contentPanel.add(name);
    }

    @Override
    protected Genre createNewEntity() {
        return new Genre();
    }
}
