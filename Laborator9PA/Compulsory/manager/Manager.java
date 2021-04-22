package manager;

import entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {
    private static EntityManagerFactory entityManagerFactory;

    private Manager() {}

    public static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("lab9");
    }

    public static void end() {
        entityManagerFactory.close();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
