package com.example.application.views;

import com.example.application.backend.classes.Book;
import com.example.application.backend.classes.Client;
import com.example.application.backend.classes.Loan;
import com.example.application.backend.daos.BookDAO;
import com.example.application.backend.daos.ClientDAO;
import com.example.application.backend.daos.LoanDAO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "admin-loan", layout = AdminViewLayout.class)
public class AdminLoanView extends VerticalLayout {

    List<Loan> loanCollection;
    Grid<Loan> grid = new Grid<>(Loan.class, false);
    FormLayout formLayout = new FormLayout();
    Loan selectedLoan;

    public AdminLoanView() {

        loanCollection = LoanDAO.getLoanList();

        gridInit();
        formInit();

        add(grid, formLayout);

    }

    private void formInit() {

        Select<Book> bookSelect = new Select<>();
        List<Book> bookList = BookDAO.getBookList();
        bookSelect.setLabel("Libros");
        bookSelect.setItems(bookList);
        bookSelect.setItemLabelGenerator(Book::getIsbn);
        Select<Client> clientSelect = new Select<>();
        List<Client> clientList = ClientDAO.getClientList();
        clientSelect.setLabel("Clientes");
        clientSelect.setItems(clientList);
        clientSelect.setItemLabelGenerator(Client::getDni);
        DatePicker loanDateField = new DatePicker("Fecha de alquiler");
        DatePicker returnDateField = new DatePicker("Fecha de devolución");

        Button addButton = new Button("Añadir");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addButton.addClickListener(e -> {

            Loan loan = new Loan(bookSelect.getValue(), clientSelect.getValue()
                    , loanDateField.getValue(), returnDateField.getValue());

            LoanDAO.addLoan(loan);

            Notification popup = Notification.show("Alquiler registrado correctamente");
            popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);


            bookSelect.clear();
            clientSelect.clear();
            loanDateField.clear();
            returnDateField.clear();

            grid.getDataProvider().refreshAll();
            UI.getCurrent().getPage().reload();

        });

        formLayout.add(bookSelect, clientSelect, loanDateField, returnDateField, addButton);

    }

    private void gridInit() {

        grid.setItems(loanCollection);
        grid.addColumn(Loan::getIdLoan).setHeader("ID");
        grid.addColumn(loan -> loan.getBook().getIsbn()).setHeader("ID Libro");
        grid.addColumn(loan -> loan.getIdClient().getDni()).setHeader("ID Cliente");
        grid.addColumn(Loan::getLoanDate).setHeader("Fecha de alquiler");
        grid.addColumn(Loan::getReturnDate).setHeader("Fecha de devolución");

        grid.addColumn(new ComponentRenderer<>(Button::new, (deleteButton, loan) -> {

            deleteLoan(deleteButton, loan);

        })).setHeader("Eiminar");

        grid.addColumn(new ComponentRenderer<>(Button::new, (updateButton, loan) -> {

            modifyLoan(updateButton, loan);

        })).setHeader("Modificar");

    }

    private void modifyLoan(Button updateButton, Loan loan) {

        updateButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        updateButton.addClickListener(e -> {

            selectedLoan = loan;

            FormLayout form = new FormLayout();
            DatePicker loanDateField = new DatePicker("Fecha de alquiler");
            loanDateField.setValue(loan.getLoanDate());
            form.add(loanDateField);
            DatePicker returnDateField = new DatePicker("Fecha de devolución");
            returnDateField.setValue(loan.getReturnDate());
            form.add(returnDateField);

            Dialog dialog = new Dialog();
            dialog.add(form);

            Button saveButton = new Button("Guardar");
            saveButton.addClickListener(e2 -> {

                selectedLoan.setLoanDate(loanDateField.getValue());
                selectedLoan.setReturnDate(returnDateField.getValue());

                LoanDAO.updateLoan(loan);
                grid.getDataProvider().refreshAll();
                grid.setItems(loanCollection);

                Notification popup = Notification.show("Alquiler modificado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                dialog.close();

            });

            form.add(saveButton);

            updateButton.setEnabled(true);

            dialog.open();

        });

        updateButton.setIcon(new Icon(VaadinIcon.EDIT));

    }

    private void deleteLoan(Button deleteButton, Loan loan) {

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        deleteButton.addClickListener(e -> {

            if (loan != null) {
                LoanDAO.removeLoanFront(loan);
                UI.getCurrent().getPage().reload();
                grid.setItems(loanCollection);

                Notification popup = Notification.show("Alquiler eliminado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

        });

        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

    }
}
