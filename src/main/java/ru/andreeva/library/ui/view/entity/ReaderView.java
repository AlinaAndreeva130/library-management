package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ru.andreeva.library.servicelayer.dao.Reader;
import ru.andreeva.library.servicelayer.repository.ReaderRepository;
import ru.andreeva.library.servicelayer.specification.ReaderSpecificationFactoryImpl;
import ru.andreeva.library.ui.component.ReaderEditor;
import ru.andreeva.library.ui.view.MainLayout;

import java.time.format.DateTimeFormatter;

@Route(value = "reader", layout = MainLayout.class)
@PageTitle("Читатели")
@Tag("reader-view")
@JsModule("./view/entity/reader-view.ts")
@UIScope
@SpringComponent
public class ReaderView extends BaseEntityView<Reader, Long, ReaderRepository> {

    public ReaderView(ReaderSpecificationFactoryImpl specificationFactory,
                      ReaderRepository repository,
                      ReaderEditor editor) {
        super(repository, specificationFactory, editor);
    }

    @Override
    protected void createColumns() {
        HeaderRow headerRow = grid.appendHeaderRow();
        filterService.addGridTextFilter(
                grid.addColumn(Reader::getFirstName).setHeader("Фамилия").setSortable(true).setKey("firstName"),
                headerRow, "Фамилия");
        filterService.addGridTextFilter(
                grid.addColumn(Reader::getLastName).setHeader("Имя").setSortable(true).setKey("lastName"), headerRow,
                "Имя");
        filterService.addGridTextFilter(
                grid.addColumn(Reader::getPatronymic).setHeader("Отчество").setSortable(true).setKey("patronymic"),
                headerRow, "Отчество");
        grid.addColumn(Reader::getClazz).setHeader("Класс").setSortable(true).setKey("clazz");
        grid.addColumn(reader -> reader.getBirthday().format(DateTimeFormatter.ofPattern("dd.MM.y")))
                .setHeader("Дата рождения")
                .setSortable(true)
                .setKey("birthday");
    }
}
