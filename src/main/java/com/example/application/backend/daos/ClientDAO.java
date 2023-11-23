package com.example.application.backend.daos;

import com.example.application.backend.classes.Client;

import javax.persistence.*;

public class ClientDAO {

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

    public static void addClient(Client client) {

        setUp();
        em.persist(client);
        close();

    }

    public static void removeClient(int idClient) {

        setUp();
        Client client = em.find(Client.class, idClient);
        if (client != null) {
            em.remove(client);
        }
        close();

    }

    public static void updateClient(Client client) {

        setUp();
        em.merge(client);
        close();

    }

    public static Client getClient(int idClient) {

        setUp();
        close();
        return em.find(Client.class, idClient);

    }
}
