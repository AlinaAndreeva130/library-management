package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.service.dao.Reader;
import ru.andreeva.library.service.repository.ReaderRepository;
import ru.andreeva.library.service.specification.ReaderSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.ReaderEditor;
import ru.andreeva.library.ui.view.MainLayout;

@Route(value = "reader", layout = MainLayout.class)
@PageTitle("Читатели")
@Tag("reader-view")
@JsModule("./view/entity/reader-view.ts")
@UIScope
@SpringComponent
public class ReaderView extends BaseEntityView<Reader, Long, ReaderRepository> {

    public ReaderView(ReaderSpecificationFactoryImpl specificationFactory, ReaderRepository repository,
                      ReaderEditor editor) {
        super(repository, specificationFactory, editor);
    }

    @Override
    protected void createColumns() {
//        grid.addColumn(User::getAddress).setHeader("Адрес").setKey("address");
    }
}
