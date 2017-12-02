package searchengine;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import searchengine.database.FileHelper;
import searchengine.database.Website;
import searchengine.index.Index;
import searchengine.index.InvertedIndex;
import searchengine.performance.TinyTimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 * The overall function of the search engine is to look through websites and return the
 * relevant websites given the query word or words.
 */

@Configuration
@EnableAutoConfiguration
@Path("/")
public class SearchEngine extends ResourceConfig {
    private static QueryHandler queryHandler;

    public SearchEngine() {
        packages("searchengine");
    }

    /**
     * The main method of our search engine program.
     * Expects exactly one argument being provided. This
     * argument is the filename of the file containing the
     * websites.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the SearchEngine!");

        if (args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        //Made a constructor in the InvertedIndex class, that takes a map, so when declaring it here
        //we are deciding which implementation to use, instead of changing it in the class itself every time
        Index index = new InvertedIndex(new HashMap<>());

        //Using the new QueryHandler class
        queryHandler = new QueryHandler(index);

        TinyTimer timer = new TinyTimer();
        timer.start();

        List<Website> sites = FileHelper.parseFile(args[0]);

        index.build(sites);

        timer.end();
        timer.printDuration();

        // run the search engine
        SpringApplication.run(SearchEngine.class);
    }

    /**
     * This methods handles requests to GET requests at search.
     * It assumes that a GET request of the form "search?query=word" is made.
     *
     * @param response Http response object
     * @param query the query string
     * @return the list of websites matching the query
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search")
    public List<Website> search(@Context HttpServletResponse response, @QueryParam("query") String query) {
        // Set crossdomain access. Otherwise your browser will complain that it does not want
        // to load code from a different location.
        response.setHeader("Access-Control-Allow-Origin", "*");


        if (query == null) {
            return new ArrayList<>();
        }

        System.out.println("Handling request for query word \"" + query + "\"");

        TinyTimer timer = new TinyTimer();
        timer.start();

        List<Website> foundSites = queryHandler.getMatchingWebsites(query);

        timer.end();
        timer.printDuration();

        if(foundSites.isEmpty()) {
            System.out.println("No website contains " + query);
        }

        System.out.println("Found " + foundSites.size() + " websites.");
        return foundSites;
    }
}
