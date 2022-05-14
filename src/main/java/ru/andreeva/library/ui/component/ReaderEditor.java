package ru.andreeva.library.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Reader;
import ru.andreeva.library.service.repository.ReaderRepository;

@SpringComponent
@UIScope
public class ReaderEditor extends BaseEditor<Reader, Long, ReaderRepository> {
    @Bind
    private TextField firstName;

    public ReaderEditor(ReaderRepository repository) {
        super(repository, Reader.class);
    }

    @Override
    protected void createContentPanel(VerticalLayout contentPanel) {
        firstName = new TextField();
        firstName.setAutofocus(true);
        contentPanel.add(firstName);
    }

    @Override
    protected Reader createNewEntity() {
        return new Reader();
    }
}
