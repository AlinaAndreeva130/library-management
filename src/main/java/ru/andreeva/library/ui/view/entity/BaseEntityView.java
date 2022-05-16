package ru.andreeva.library.ui.view.entity;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.template.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.specification.SpecificationFactory;
import ru.andreeva.library.ui.component.BaseEditor;
import ru.andreeva.library.ui.filter.GridFilterService;

import javax.annotation.PostConstruct;

public abstract class BaseEntityView<T, ID, R extends JpaSpecificationExecutor<T> & JpaRepository<T, ID>> extends LitTemplate {
    @Id("grid")
    protected Grid<T> grid;
    protected final R repository;
    protected final GridFilterService<T, ID, R> filterService;
    private final BaseEditor<T, ID, R> editor;

    @Id("action-panel")
    protected HorizontalLayout actionPanel;
    @Id("add-btn")
    protected Button addBtn;
    @Id("edit-btn")
    protected Button editBtn;
    @Id("delete-btn")
    protected Button deleteBtn;
    @Id("update-btn")
    protected Button updateBtn;

    public BaseEntityView(R repository, SpecificationFactory<T> specificationFactory, BaseEditor<T, ID, R> editor) {
        this.repository = repository;
        this.editor = editor;
        filterService = new GridFilterService<>(this.repository, grid, specificationFactory);
        grid.addSelectionListener(event -> refreshActionPanel());
        grid.setHeightByRows(true);
        createActionPanel();
        createColumns();
    }

    private void createActionPanel() {
        configureActionPanel();
    }

    protected void configureActionPanel() {
        if (editor == null) {
            addBtn.setVisible(false);
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }

        actionPanel.setPadding(true);
        addBtn.setIcon(new Icon(VaadinIcon.PLUS));
        addBtn.addClickListener(this::addEntity);

        editBtn.setIcon(new Icon(VaadinIcon.EDIT));
        editBtn.addClickListener(this::editEntity);

        deleteBtn.setIcon(new Icon(VaadinIcon.TRASH));
        deleteBtn.addClickListener(this::deleteEntity);

        updateBtn.setIcon(new Icon(VaadinIcon.REFRESH));
        updateBtn.addClickListener(event -> filterService.refresh());

        refreshActionPanel();
    }

    protected boolean refreshActionPanel() {
        boolean isItemSelected = !grid.getSelectedItems().isEmpty();
        editBtn.setEnabled(isItemSelected);
        deleteBtn.setEnabled(isItemSelected);
        return isItemSelected;
    }

    private void addEntity(ClickEvent<Button> event) {
        editor.addEntity(this::actionAfterCloseEditor);
    }

    private void editEntity(ClickEvent<Button> event) {
        if (!grid.getSelectedItems().isEmpty()) {
            T selectedItem = grid.getSelectedItems().iterator().next();
            editor.editEntity(selectedItem, this::actionAfterCloseEditor);
        }
    }

    private void deleteEntity(ClickEvent<Button> event) {
        if (!grid.getSelectedItems().isEmpty()) {
            T deletingItem = grid.getSelectedItems().iterator().next();
            editor.deleteEntity(deletingItem, this::actionAfterCloseEditor);
        }
    }

    private void actionAfterCloseEditor() {
        filterService.refresh();
        refreshActionPanel();
    }

    protected abstract void createColumns();

    protected void initAfterConstructionObject() {

    }

    @PostConstruct
    private void postConstruct() {
        initAfterConstructionObject();
    }

}