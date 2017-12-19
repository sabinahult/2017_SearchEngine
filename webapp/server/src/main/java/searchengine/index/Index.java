package searchengine.index;

import searchengine.database.Website;

import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

 public interface Index {

    /**
     * Takes a list of websites, that can then be used as a basis for creating a data structure.
     * @param listOfWebsites a List of website objects
     */
    void build(List<Website> listOfWebsites);


    /**
     * Takes the query word and looks through the database of websites
     * @param word Should contain the query word
     * @return Returns a List of website objects, that contain the given query word if found. If not
     * found it returns an empty List.
     */
    List<Website> lookup(String word);


    /**
    * Calculates the total number of websites in the database. (IDF)
    * @return Returns a int of the size of the database
    */
   int getNumberOfWebsites();


   /**
     * Returns a list of all the website in the database. (BM25)
     * @return a List of Websites.
     */
   List<Website> getWebsites();
}