package com.example.application.backend.classes;



import com.example.application.backend.enums.Category;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBook;
    private String title;
    private String isbn;
    private int pages;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "idAuthor")
    private Author author;
    @OneToOne(mappedBy = "idBook")
    private Loan loan;

    public Book() {
    }

    public Book(String title, String isbn, int pages, Author author, Category category) {
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.author = author;
        this.category = category;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return idBook == book.idBook && pages == book.pages && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && category == book.category && Objects.equals(author, book.author) && Objects.equals(loan, book.loan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, title, isbn, pages, category, author, loan);
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pages=" + pages +
                ", category=" + category +
                ", author=" + author +
                ", loan=" + loan +
                '}';
    }

}
