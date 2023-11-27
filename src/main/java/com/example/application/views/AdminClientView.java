package com.example.application.views;

import com.example.application.backend.classes.Client;
import com.example.application.backend.daos.ClientDAO;
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

@Route(value = "admin-client", layout = AdminViewLayout.class)
public class AdminClientView extends VerticalLayout {

    List<Client> clientCollection;
    Grid<Client> grid = new Grid<>(Client.class, false);
    FormLayout formLayout = new FormLayout();
    Client selectedClient;

    public AdminClientView() {

        clientCollection = ClientDAO.getClientList();

        gridInit();
        formInit();

        add(grid, formLayout);

    }

    private void formInit() {

        TextField dniField = new TextField("DNI");
        TextField nameField = new TextField("Nombre");
        TextField emailField = new TextField("Email");
        TextField addressField = new TextField("Dirección");

        Button addButton = new Button("Añadir");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addButton.addClickListener(e -> {

            Client client = new Client(dniField.getValue(), nameField.getValue()
                    , emailField.getValue(), addressField.getValue());

            ClientDAO.addClient(client);

            Notification popup = Notification.show("Cliente añadido correctamente");
            popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            dniField.clear();
            nameField.clear();
            emailField.clear();
            addressField.clear();

            grid.getDataProvider().refreshAll();
            UI.getCurrent().getPage().reload();
        });

        formLayout.add(dniField, nameField, emailField, addressField, addButton);

    }

    private void gridInit() {

        grid.setItems(clientCollection);
        grid.addColumn(Client::getIdClient).setHeader("ID");
        grid.addColumn(Client::getDni).setHeader("DNI");
        grid.addColumn(Client::getName).setHeader("Nombre");
        grid.addColumn(Client::getEmail).setHeader("Email");
        grid.addColumn(Client::getAddress).setHeader("Address");

        grid.addColumn(new ComponentRenderer<>(Button::new, (deleteButton, client) -> {

            deleteClient(deleteButton, client);

        })).setHeader("Eiminar");

        grid.addColumn(new ComponentRenderer<>(Button::new, (updateButton, client) -> {

            modifyClient(updateButton, client);

        })).setHeader("Modificar");
    }

    private void modifyClient(Button updateButton, Client client) {

        updateButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        List<Client> clientList = ClientDAO.getClientList();

        updateButton.addClickListener(e -> {

            selectedClient = client;

            FormLayout form = new FormLayout();
            TextField dniField = new TextField("DNI");
            dniField.setValue(client.getDni());
            form.add(dniField);
            TextField nameField = new TextField("Nombre");
            nameField.setValue(client.getName());
            form.add(nameField);
            TextField emailField = new TextField("Email");
            emailField.setValue(client.getEmail());
            form.add(emailField);
            TextField addressField = new TextField("Dirección");
            addressField.setValue(client.getAddress());
            form.add(addressField);

            Dialog dialog = new Dialog();
            dialog.add(form);

            Button saveButton = new Button("Guardar");
            saveButton.addClickListener(e2 -> {

                selectedClient.setDni(dniField.getValue());
                selectedClient.setName(nameField.getValue());
                selectedClient.setEmail(emailField.getValue());
                selectedClient.setAddress(addressField.getValue());

                ClientDAO.updateClient(client);
                grid.getDataProvider().refreshAll();
                grid.setItems(clientCollection);

                Notification popup = Notification.show("Cliente modificado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                dialog.close();

            });

            form.add(saveButton);

            updateButton.setEnabled(true);

            dialog.open();

        });

        updateButton.setIcon(new Icon(VaadinIcon.EDIT));
    }

    private void deleteClient(Button deleteButton, Client client) {

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        deleteButton.addClickListener(e -> {

            if (client != null) {
                ClientDAO.removeClientFront(client);
                //UI.getCurrent().getPage().reload();
                grid.setItems(clientCollection);

                Notification popup = Notification.show("Cliente eliminado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

        });

        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

    }

}
