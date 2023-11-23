package com.example.application.views;

import com.example.application.backend.classes.Book;
import com.example.application.backend.daos.BookDAO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import java.util.ArrayList;
import java.util.List;

@Route(value = "admin-book")
public class AdminBookView {

    List<Book> bookCollection = new ArrayList<>();

    Grid<Book> grid = new Grid<>(Book.class, false);

    public AdminBookView() {

        bookCollection = BookDAO.getBookList();

        gridInit();

    }

    private void gridInit() {

        grid.setItems(bookCollection);
        grid.addColumn(Book::getIdBook).setHeader("ID");
        grid.addColumn(Book::getTitle).setHeader("Título");
        grid.addColumn(Book::getIsbn).setHeader("ISBN");
        grid.addColumn(Book::getAuthor).setHeader("Autor");
        grid.addColumn(Book::getPages).setHeader("Páginas");
        grid.addColumn(Book::getCategory).setHeader("Valoración");

        grid.addColumn(new ComponentRenderer<>(Button::new, (deleteButton, book) -> {

            deleteBook(deleteButton, book);

        })).setHeader("Eiminar");

        grid.addColumn(new ComponentRenderer<>(Button::new, (updateButton, book) -> {

            modifyBook(updateButton, book);

        })).setHeader("Modificar");

    }

    private void modifyBook(Button updateButton, Book book) {


    }

    private void deleteBook(Button deleteButton, Book book) {

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        deleteButton.addClickListener(e -> {

            if (book != null) {
                BookDAO.removeBookFront(book);
                UI.getCurrent().getPage().reload();

                Notification popup = Notification.show("Libro eliminado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

        });

        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));

    }

}
