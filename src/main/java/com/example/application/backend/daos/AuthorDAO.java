package com.example.application.backend.daos;


import com.example.application.backend.classes.Author;
import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AuthorDAO {

    private static final Logger LOG = Logger.getLogger(AuthorDAO.class);
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

    public static void removeAuthorFront(Author author) {

        setUp();

        em.remove(em.merge(author));

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

    public static List<Author> getAuthorList() {

        List<Author> authorList;
        setUp();

        authorList = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();

        if (authorList.isEmpty()) {
            LOG.info("No hay datos en la tabla autores");
        } else {
            for (Author author : authorList) {
                LOG.info(author.getIdAuthor() + "--" + author.getName() + "--" + author.getSurnames() + "--" + author.getNationality());
            }
        }

        close();

        return authorList;
    }
}

