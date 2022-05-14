package ru.andreeva.library.ui.view;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Route(value = "", layout = MainLayout.class)
@Tag("main-view")
@PageTitle("Главная")
@JsModule("./view/main-view.ts")
@UIScope
@SpringComponent
public class MainView extends LitTemplate {
    @Id("text")
    private Label testText;

    public MainView() {
        testText.setText("dsadasd");
    }
}
