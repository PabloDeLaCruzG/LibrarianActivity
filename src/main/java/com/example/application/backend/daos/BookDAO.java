package com.example.application.backend.daos;

import com.example.application.backend.classes.Book;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hibernate.bytecode.BytecodeLogging.LOGGER;

public class BookDAO {

    private static final Logger LOG = Logger.getLogger(BookDAO.class);
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

    public static void addBook(Book book) {

            setUp();
            em.persist(book);
            close();

    }

    public static void removeBook(int idBook) {

            setUp();
            Book book = em.find(Book.class, idBook);
            if (book != null) {
                em.remove(book);
            }
            close();

    }

    public static void removeBookFront(Book book) {

        setUp();

        em.remove(em.merge(book));

        close();
    }

    public static void updateBook(Book book) {

            setUp();
            em.merge(book);
            close();

    }

    public static Book getBook(int idBook) {

            setUp();
            Book book = em.find(Book.class, idBook);
            if (book == null) {
                return null;
            }
            close();
            return book;

    }

    public static List<Book> getBookList() {

        List<Book> bookList = new ArrayList<>();
        setUp();

        bookList = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();

        if (bookList.isEmpty()) {
            LOG.info("No hay datos en la tabla equipo");
        } else {
            for (Book book : bookList) {
                long idBook = book.getIdBook();
                LOG.info(idBook + "--" + book.getTitle() + "--" + book.getIsbn() + "--" + book.getAuthor() + "--" + String.valueOf(book.getCategory()) + "--" + book.getPages());
            }
        }

        close();

        return bookList;
    }
}



