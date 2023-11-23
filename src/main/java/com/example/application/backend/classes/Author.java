package com.example.application.backend.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAuthor;
    private String name;
    private String surnames;
    private String nationality;
    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public Author() {

    }

    public Author(String name, String surnames, String nationality) {
        this.name = name;
        this.surnames = surnames;
        this.nationality = nationality;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() { return surnames; }

    public void setSurnames(String surnames) { this.surnames = surnames; }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    @Override
    public String toString() {
        return "Autor{" +
                "idAuthor=" + idAuthor +
                ", nombre='" + name + '\'' +
                ", apellidos='" + surnames + '\'' +
                ", nacionalidad='" + nationality + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return idAuthor == author.idAuthor && Objects.equals(name, author.name) && Objects.equals(surnames, author.surnames) && Objects.equals(nationality, author.nationality) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuthor, name, surnames, nationality, books);
    }
}