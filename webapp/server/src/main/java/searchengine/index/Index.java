package searchengine.index;

import searchengine.database.Website;

import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

 public interface Index {

    /**
     * Takes a list of websites, that is used to create a data structure to search through.
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
    * To calculate the IDF-score we need to know the number of Websites
    * @return Returns a int of the size of the database
    */
   int getNumberOfWebsites();


   /**
     * To be able to calculate the BM25 score, we to need get the List of Websites.
     * @return a List of Websites.
     */
   List<Website> getWebsites();
}