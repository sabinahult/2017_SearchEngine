package searchengine.index;

import searchengine.database.Website;

import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

 public interface Index {

    /**
     * Takes a list of websites, that can then be used as a basis for creating a data structure that is easier to search through.
     * @param listOfWebsites should be a List of website objects
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
    * To be able to calculate the IDF-score we need a List of Websites
    * @return Returns a int of the size of the database
    */
   int getNumberOfWebsites();

    /**
     * To be able to calulate the BM25 score, we to need to return the List of Websites.
     * @return a List of Websites.
     */
   List<Website> getWebsites();
}