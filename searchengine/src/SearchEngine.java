import java.util.List;
import java.util.Scanner;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
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

        TinyTimer timer = new TinyTimer();

        //Debugging to see contents of websites array after it's created from reading the file
        //System.out.println(sites.toString());

        System.out.println("Please provide a query word");

        //SEARCH LOOP
        while (sc.hasNext()) {
            String line = sc.nextLine();

            timer.start();

            //SIMPLE INDEX - Comment out if using inverted index
            //SimpleIndex index = new SimpleIndex();
            //index.build(sites);

            //INVERTED INDEX - Comment out if using simple index
            InvertedIndex index = new InvertedIndex();
            index.build(sites);

            foundSites = index.lookup(line);

            if(foundSites.isEmpty()) {
                System.out.println("No website contains " + line + "." ); //Assignment 2
            }

            else {
                for(Website w : foundSites)
                System.out.println("Query is found on '" + w.getUrl() + "'");
            }

            timer.end();
            timer.printDuration();

            System.out.println("Please provide the next query word");
        }
    }
}

