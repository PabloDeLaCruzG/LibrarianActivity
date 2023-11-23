package com.example.application.backend.daos;



import com.example.application.backend.classes.Author;

import javax.persistence.*;

public class AuthorDAO {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    private static void setUp() {

        emf = Persistence.createEntityManagerFactory("Librarian");
        em = emf.createEntityManager();
        em.getTransaction().begin();

    }

    private static void close() {

        em.getTransaction().commit();
        em.close();

    }

    public static void addAuthor(Author author) {

        setUp();
        em.persist(author);
        close();

    }

    public static void removeAuthor(int idAuthor) {

        setUp();
        Author author = em.find(Author.class, idAuthor);
        if (author != null) {
            em.remove(author);
        }
        close();

    }

    public static void updateAuthor(Author author) {

        setUp();
        em.merge(author);
        close();

    }

    public static Author getAuthor(int idAuthor) {

        setUp();
        close();
        return em.find(Author.class, idAuthor);

    }
}

