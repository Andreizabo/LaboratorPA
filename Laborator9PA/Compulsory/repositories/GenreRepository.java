package repositories;

import entities.Genre;
import manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GenreRepository {
    public static void createGenre(Genre genre) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(genre);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Genre findGenreById(int id) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Genre genre = entityManager.find(Genre.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return genre;
    }

    public static List<Genre> findGenreByName(String name) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        Query q = entityManager.createNamedQuery("Genre.findByName").setParameter("name", name);
        entityManager.getTransaction().commit();

        List<Genre> gr = q.getResultList();

        entityManager.close();

        return gr;
    }
}
