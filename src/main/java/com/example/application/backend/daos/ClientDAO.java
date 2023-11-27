package com.example.application.backend.daos;

import com.example.application.backend.classes.Client;
import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ClientDAO {

    private static final Logger LOG = Logger.getLogger(ClientDAO.class);
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

    public static void removeClientFront(Client client) {

        setUp();

        em.remove(em.merge(client));

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

    public static List<Client> getClientList() {

        List<Client> clientList;
        setUp();

        clientList = em.createQuery("SELECT c FROM Client c", Client.class).getResultList();

        if (clientList.isEmpty()) {
            LOG.info("No hay datos en la tabla clientes");
        } else {
            for (Client client : clientList) {
                LOG.info(client.getIdClient() + "--" + client.getDni() + "--" + client.getName() + "--" + client.getEmail() + "--" + client.getAddress());
            }
        }

        close();

        return clientList;
    }

}
