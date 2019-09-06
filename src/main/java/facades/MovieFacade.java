package facades;

import entities.Movie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = getEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = getEntityManager();
        try {
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m").getResultList();
            return movies;
        } finally {
            em.close();
        }
    }

    public List<Movie> getMovieWithName(String name) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movie m where m.name = " + name, Movie.class);
                    
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Movie getMovieWithID(long id) {
        EntityManager em = getEntityManager();
        try {
            Movie movie = em.createQuery("SELECT m FROM Movie m where m.id=" + id, Movie.class)
                    .getSingleResult();
            return movie;
        } finally {
            em.close();
        }
    }
}

    


