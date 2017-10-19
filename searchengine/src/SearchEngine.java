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
        if (args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        Scanner sc = new Scanner(System.in);
        List<Website> sites = FileHelper.parseFile(args[0]);
        List<Website> foundSites;

        //Debugging to see contents of websites array after it's created from reading the file
        //System.out.println(sites.toString());

        Index index = new InvertedIndex();
        index.build(sites);

        System.out.println("Please provide a query word");

        //SEARCH LOOP
        while (sc.hasNext()) {
            String line = sc.nextLine();

           foundSites = index.lookup(line);

            if(foundSites.isEmpty()) {
                System.out.println("No website contains " + line); //Assignment 2
            } else {
                for(Website w : foundSites)
                System.out.println("Query is found on '" + w.getUrl() + "'");
            }

            System.out.println("Please provide the next query word");
        }
    }
}

