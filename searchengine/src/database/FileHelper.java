package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 * The overall function of the FileHelper class is to construct a database of websites from a dataset which will
 * be the database the search engine queries.
 */

public class FileHelper {

    /**
     * @param filename This is the dataset that is the foundation for the database
     * @return Returns a list of websites that contains the query word or words
     */

    public static List<Website> parseFile(String filename) {
        List<Website> sites = new ArrayList<>();
        String url = null;
        String title = null;
        List<String> listOfWords = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(filename), "UTF-8");

            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.startsWith("*PAGE:")) {

                    //If all variables already has a value (because this is not the first iteration),
                    //then create website from data gathered in previous iteration
                    if (isEntryValid(url, title, listOfWords)) {
                        sites.add(new Website(url, title, listOfWords));
                    }

                    //New website is being read in
                    url = line.substring(6);
                    title = null;
                    listOfWords.clear();

                } else if (title == null) {
                    title = line;

                } else {
                    listOfWords.add(line);
                }
            }

            //This part is to add the last entry to the sites list
            if (isEntryValid(url, title, listOfWords)) {
                sites.add(new Website(url, title, listOfWords));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the given file");
           e.printStackTrace();
        }

        return sites;
    }

    /**
     * Checking if entries contains what is needed to create a website object
     * @param url A string formatted as an url
     * @param title Title of the website
     * @param listOfWords The list of words contained on the website
     * @return True if all parameters contains a value
     */
    private static boolean isEntryValid(String url, String title, List<String> listOfWords) {
        return url != null && title != null && !listOfWords.isEmpty();
    }
}