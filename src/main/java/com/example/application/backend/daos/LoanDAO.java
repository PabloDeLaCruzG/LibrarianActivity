package com.example.application.backend.daos;

import com.example.application.backend.classes.Loan;

import javax.persistence.*;

public class LoanDAO {

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
}

