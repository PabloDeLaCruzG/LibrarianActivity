package com.example.application.views;

import com.example.application.backend.classes.Author;
import com.example.application.backend.classes.Book;
import com.example.application.backend.daos.AuthorDAO;
import com.example.application.backend.daos.BookDAO;
import com.example.application.backend.enums.Category;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Admin Book")
@Route(value = "")
public class AdminBookView extends VerticalLayout {

    List<Book> bookCollection = new ArrayList<>();
    Grid<Book> grid = new Grid<>(Book.class, false);
    FormLayout formLayout = new FormLayout();
    Book selectedBook;

    public AdminBookView() {

        bookCollection = BookDAO.getBookList();

        gridInit();
        formInit();

        add(grid, formLayout);

    }

    private void formInit() {

        TextField titleField = new TextField("Título");
        TextField isbnField = new TextField("ISBN");
        IntegerField pagesField = new IntegerField("Páginas");
        Select<Category> categoryField = new Select<>();
        categoryField.setLabel("Categoria");
        categoryField.setItems(Category.values());
        Select<Author> authorSelect = new Select<>();
        List<Author> authorList = AuthorDAO.getAuthorList();
        authorSelect.setLabel("Autores");
        authorSelect.setItems(authorList);
        authorSelect.setItemLabelGenerator(Author::getName);

        Button addButton = new Button("Añadir");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addButton.addClickListener(e -> {

            Book book = new Book(titleField.getValue(), isbnField.getValue()
                    , pagesField.getValue(), authorSelect.getValue()
                    , categoryField.getValue());

            BookDAO.addBook(book);

            Notification popup = Notification.show("Libro añadido correctamente");
            popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            titleField.clear();
            isbnField.clear();
            pagesField.clear();
            categoryField.clear();
            authorSelect.clear();

            grid.getDataProvider().refreshAll();
            grid.setItems(bookCollection);

            UI.getCurrent().getPage().reload();
        });

        formLayout.add(titleField, isbnField, pagesField,
                categoryField, authorSelect, addButton);

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

        updateButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_TERTIARY);

        List<Author> authorList = AuthorDAO.getAuthorList();

        updateButton.addClickListener(e -> {

            selectedBook = book;

            FormLayout form = new FormLayout();
            TextField titleField = new TextField("Título");
            titleField.setValue(book.getTitle());
            form.add(titleField);
            TextField isbnField = new TextField("ISBN");
            isbnField.setValue(book.getIsbn());
            form.add(isbnField);
            IntegerField pagesField = new IntegerField("Páginas");
            pagesField.setValue(book.getPages());
            form.add(pagesField);
            Select<Category> categoryField = new Select<>();
            categoryField.setLabel("Valoracion");
            categoryField.setItems(Category.values());
            categoryField.setValue(book.getCategory());
            form.add(categoryField);

            Select<Author> authorSelect = new Select<>();
            authorSelect.setLabel("Autores");
            authorSelect.setItems(authorList);
            authorSelect.setValue(book.getAuthor());
            form.add(authorSelect);

            Dialog dialog = new Dialog();
            dialog.add(form);

            Button saveButton = new Button("Guardar");
            saveButton.addClickListener(e2 -> {

                selectedBook.setTitle(titleField.getValue());
                selectedBook.setIsbn(isbnField.getValue());
                selectedBook.setPages(pagesField.getValue());
                selectedBook.setCategory(categoryField.getValue());
                selectedBook.setAuthor(authorSelect.getValue());
                selectedBook = book;

                BookDAO.updateBook(book);
                grid.getDataProvider().refreshAll();
                grid.setItems(bookCollection);

                Notification popup = Notification.show("Libro modificado correctamente");
                popup.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                dialog.close();

            });

            form.add(saveButton);

            updateButton.setEnabled(true);

            dialog.open();

        });

        updateButton.setIcon(new Icon(VaadinIcon.EDIT));

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
