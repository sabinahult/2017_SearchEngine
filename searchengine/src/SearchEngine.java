import database.FileHelper;
import database.Website;
import index.Index;
import index.InvertedIndex;
import performance.TinyTimer;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 * The overall function of the search engine is to look through websites and return the
 * relevant websites given the query word or words.
 */

public class SearchEngine {
    public static void main(String[] args) {
        System.out.println("Welcome to the SearchEngine!");
        if(args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        Scanner sc = new Scanner(System.in);
        List<Website> sites = FileHelper.parseFile(args[0]);
        List<Website> foundSites;

        //Made a constructor in the InvertedIndex class, that takes a map, so when declaring it here
        //we are deciding which implementation to use, instead of changing it in the class itself every time
        Index index = new InvertedIndex(new HashMap<>());
        index.build(sites);

        //Using the new QueryHandler class
        QueryHandler queryHandler = new QueryHandler(index);

        System.out.println("Please provide a query word");

        //SEARCH LOOP
        while(sc.hasNext()) {
            TinyTimer timer = new TinyTimer();
            timer.start();

            String line = sc.nextLine();
            foundSites = queryHandler.getMatchingWebsites(line);

            if(foundSites.isEmpty()) {
                System.out.println("No website contains " + line);
            } else {
                for(Website w : foundSites)
                System.out.println(line + " is found on '" + w.getUrl() + "'");
            }

            timer.end();
            timer.printDuration();

            System.out.println("Please provide the next query word");
        }
    }
}