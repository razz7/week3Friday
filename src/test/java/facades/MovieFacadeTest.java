package facades;

import utils.EMF_Creator;
import entities.Movie;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = MovieFacade.getFacadeExample(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

// Setup the DataBase in a known state BEFORE EACH TEST
//TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie("Rasmus Klump", 10, 2001, Arrays.asList("Rasmus Hemmingsen")));
            em.persist(new Movie("Blues Brothers", 5, 1980, Arrays.asList("John John", "Hans Hans")));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void movieIsEqual() {
        Movie movieExp = new Movie("Rasmus Klump", 10, 2001, Arrays.asList("Rasmus Hemmingsen"));
        Movie movieExp1 = new Movie("Rasmus Klump", 10, 2001, Arrays.asList("Rasmus Hemmingsen"));
        assertThat(movieExp.getName(), equalTo(movieExp1.getName()));

    }

    @Test
    public void getAllMovies() {
        List<Movie> movies = facade.getAllMovies();
        assertEquals(movies.size(), 2);
    }
    
    @Test
    public void testArray() {
        List<Movie> movies = facade.getAllMovies();
        Matchers.hasItemInArray(movies.get(0));
    }

}
