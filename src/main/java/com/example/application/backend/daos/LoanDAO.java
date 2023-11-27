package com.example.application.backend.daos;

import com.example.application.backend.classes.Loan;
import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class LoanDAO {
    private static final Logger LOG = Logger.getLogger(LoanDAO.class);
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

    public static void addLoan(Loan loan) {

        setUp();
        em.persist(loan);
        close();

    }

    public static void removeLoan(int idLoan) {

        setUp();
        Loan loan = em.find(Loan.class, idLoan);
        if (loan != null) {
            em.remove(loan);
        }
        close();

    }

    public static void removeLoanFront(Loan loan) {

        setUp();

        em.remove(em.merge(loan));

        close();
    }

    public static void updateLoan(Loan loan) {

        setUp();
        em.merge(loan);
        close();

    }

    public static Loan getLoan(int idLoan) {

        setUp();
        close();
        return em.find(Loan.class, idLoan);

    }

    public static List<Loan> getLoanList() {

        List<Loan> loanList;
        setUp();

        loanList = em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();

        if (loanList.isEmpty()) {
            LOG.info("No hay datos en la tabla alquileres");
        } else {
            for (Loan loan : loanList) {
                LOG.info(loan.getIdLoan() + "--" + loan.getIdClient().getIdClient() + "--" + loan.getBook().getIdBook() + "--" + loan.getLoanDate() + "--" + loan.getReturnDate());
            }
        }

        close();

        return loanList;
    }
}

