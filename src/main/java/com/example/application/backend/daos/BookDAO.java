package com.example.application.backend.daos;

import com.example.application.backend.classes.Book;
import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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
        book = em.merge(book);
        em.persist(book);
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

        List<Book> bookList;
        setUp();

        bookList = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();

        if (bookList.isEmpty()) {
            LOG.info("No hay datos en la tabla libros");
        } else {
            for (Book book : bookList) {
                LOG.info(book.getIdBook() + "--" + book.getTitle() + "--" + book.getIsbn() + "--" + book.getAuthor() + "--" + String.valueOf(book.getCategory()) + "--" + book.getPages());
            }
        }

        close();

        return bookList;
    }
}



