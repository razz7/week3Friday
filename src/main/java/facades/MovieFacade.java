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
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m").getResultList();
            return movies;
        } finally {
            em.close();
        }
    }

    public List<Movie> getMovieWithName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movie m where m.name = " + name, Movie.class);
                    
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Movie getMovieWithID(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.createQuery("SELECT m FROM Movie m where m.id=" + id, Movie.class)
                    .getSingleResult();
            return movie;
        } finally {
            em.close();
        }
    }
    public static void main(String[] args) {
        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        List<String> jamesBondCast = Arrays.asList("Daniel Craig","Naomie Watts");
        em.persist(new Movie("James Bond", 4, 2004, jamesBondCast));
        List<String> TGGCast = Arrays.asList("Leonardo Dicaprio","Toby Mcquire");
        em.persist(new Movie("The Great Gatsby", 9, 2010, TGGCast));
        List<String> spidermanCast = Arrays.asList("Andrew Garfield","Emma Stone");
        em.persist(new Movie("Spiderman 1", 8, 2006, spidermanCast));
        List<String> LOTRCast = Arrays.asList("Viggo Mortensen","Orlando Bloom", "Ian McKellen", "Liv Tyler");
        em.persist(new Movie("Lord Of the Rings", 10, 2003, LOTRCast));
        List<String> BadNeighbours = Arrays.asList("Seth Rogen","Zac Efron");
        em.persist(new Movie("Bad Neighbours", 2, 2014, BadNeighbours));
        List<String> peterPlysCast = Arrays.asList("Peter Plys","Æsel", "Tigerdyr");
        em.persist(new Movie("Peter Plys", 10, 2000, peterPlysCast));
        em.getTransaction().commit();
        em.close();
    }
    
    }


