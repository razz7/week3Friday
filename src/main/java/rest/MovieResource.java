package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.research.ws.wadl.Param;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"serverIsRunning\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() {
        List<Movie> list = FACADE.getAllMovies();
        String g = GSON.toJson(list);
        return g;
    }
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieWithIDPage(@PathParam("id") long id) {
        Movie movie = FACADE.getMovieWithID(id);
        String g = GSON.toJson(movie);
        return g;
    }
    @GET    
    @Path("/name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMoviesWithNamePage(@PathParam("name") String name) {
        List<Movie> movie = FACADE.getMovieWithName(name);
        String g = GSON.toJson(movie);
        return g;
    }
    
    @GET    
    @Path("getdummydatalichking")
    @Produces({MediaType.APPLICATION_JSON})
    public String createDummyData() {
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
        return "done";
    }
        
        
    }
    
        
    

  
    



