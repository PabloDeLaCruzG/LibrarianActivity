package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        VerticalLayout welcomeLayout = new VerticalLayout();
        welcomeLayout.setSizeUndefined();

        welcomeLayout.getStyle().set("text-align", "center");
        welcomeLayout.getStyle().set("font-size", "46px"); // Tamaño de la fuente
        welcomeLayout.getStyle().set("margin-bottom", "20px");
        welcomeLayout.getStyle().set("color", "#333");
        welcomeLayout.add("¡Bienvenido a la administración de la Libreria Pablo!");

        Button goToNextViewButton = new Button("Entrar");
        goToNextViewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goToNextViewButton.getStyle().set("font-size", "34px"); // Tamaño de la fuente
        goToNextViewButton.getStyle().set("padding", "30px 30px");
        goToNextViewButton.getStyle().set("background-color", "#4CAF50");
        goToNextViewButton.getStyle().set("color", "white");
        goToNextViewButton.getStyle().set("border", "none");
        goToNextViewButton.getStyle().set("border-radius", "5px");
        goToNextViewButton.getStyle().set("cursor", "pointer");
        goToNextViewButton.getStyle().set("text-align", "center");
        goToNextViewButton.getStyle().set("transition", "background-color 0.3s ease");


        goToNextViewButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(AdminViewLayout.class)));

        welcomeLayout.add(goToNextViewButton);

        welcomeLayout.setAlignItems(Alignment.CENTER);

        add(welcomeLayout);
    }

}
