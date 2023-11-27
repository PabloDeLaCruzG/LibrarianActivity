package com.example.application.views;

import com.example.application.backend.classes.Author;
import com.example.application.backend.daos.AuthorDAO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "admin-author", layout = AdminViewLayout.class)
public class AdminAuthorView extends VerticalLayout {

    List<Author> authorCollection;
    Grid<Author> grid = new Grid<>(Author.class, false);
    FormLayout formLayout = new FormLayout();
    Author selectedAuthor;

    public AdminAuthorView() {

        authorCollection = AuthorDAO.getAuthorList();

        gridInit();
        formInit();

        add(grid, formLayout);
    }

    private void formInit() {

        TextField nameField = new TextField("Nombre");
        TextField surnamesField = new TextField("Apellidos");
        TextField nationaliyField = new TextField("Nacionalidad");

        Button addButton = new Button("Añadir");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addButton.addClickListener(e -> {

            Author author = new Author(nameField.getValue(), surnamesField.getValue(), nationaliyField.getValue());

            AuthorDAO.addAuthor(author);

            Notification popup = Notification.show("Autor añadido correctamente");
            popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            nameField.clear();
            surnamesField.clear();
            nameField.clear();

            grid.getDataProvider().refreshAll();
            grid.setItems(authorCollection);

            UI.getCurrent().getPage().reload();

        });

        formLayout.add(nameField, surnamesField, nationaliyField, addButton);

    }

    private void gridInit() {

        grid.setItems(authorCollection);
        grid.addColumn(Author::getIdAuthor).setHeader("ID");
        grid.addColumn(Author::getName).setHeader("Nombre");
        grid.addColumn(Author::getSurnames).setHeader("Apelllidos");
        grid.addColumn(Author::getNationality).setHeader("Nacionalidad");

        grid.addColumn(new ComponentRenderer<>(Button::new, (deleteButton, author) -> {

            deleteAuthor(deleteButton, author);

        })).setHeader("Eiminar");

        grid.addColumn(new ComponentRenderer<>(Button::new, (updateButton, author) -> {

            modifyAuthor(updateButton, author);

        })).setHeader("Modificar");


    }

    private void modifyAuthor(Button updateButton, Author author) {

        updateButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        updateButton.addClickListener(e -> {

            selectedAuthor = author;

            FormLayout form = new FormLayout();
            TextField nameField = new TextField("Nombre");
            nameField.setValue(author.getName());
            form.add(nameField);
            TextField surnamesField = new TextField("Apellidos");
            surnamesField.setValue(author.getSurnames());
            form.add(surnamesField);
            TextField nationaliyField = new TextField("Nacionalidad");
            nationaliyField.setValue(author.getNationality());
            form.add(nationaliyField);

            Dialog dialog = new Dialog();
            dialog.add(form);


            Button saveButton = new Button("Guardar");

            saveButton.addClickListener(e2 -> {

                selectedAuthor.setName(nameField.getValue());
                selectedAuthor.setSurnames(surnamesField.getValue());
                selectedAuthor.setNationality(nationaliyField.getValue());
                selectedAuthor = author;

                AuthorDAO.updateAuthor(author);
                grid.getDataProvider().refreshAll();
                grid.setItems(authorCollection);

                Notification popup = Notification.show("Autor modificado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                dialog.close();

            });

            form.add(saveButton);

            updateButton.setEnabled(true);

            dialog.open();

        });

        updateButton.setIcon(new Icon(VaadinIcon.EDIT));

    }

    private void deleteAuthor(Button deleteButton, Author author) {

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        deleteButton.addClickListener(e -> {

            if (author != null) {
                AuthorDAO.removeAuthorFront(author);
                UI.getCurrent().getPage().reload();

                Notification popup = Notification.show("Autor eliminado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

        });

        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

    }


}
