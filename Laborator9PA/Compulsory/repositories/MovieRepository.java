package repositories;

import entities.Movie;
import manager.Manager;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.util.List;

public class MovieRepository {
    public static void createMovie(Movie movie) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(movie);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Movie findMovieById(int id) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return movie;
    }

    public static List<Movie> findMovieByName(String name) {
        EntityManager entityManager = Manager.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        Query q = entityManager.createNamedQuery("Movie.findByName").setParameter("name", name);
        entityManager.getTransaction().commit();

        List<Movie> mvs = q.getResultList();

        entityManager.close();

        return mvs;
    }
}
