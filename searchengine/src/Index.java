import java.util.List;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

 public interface Index {

    /**
     * Takes a List a websites, that can then be used as a basis for creating a different data structure
     * @param listOfWebsites should be a List of website objects
     */
    void build(List<Website> listOfWebsites);

    /**
     * Takes a word and looks through the database of websites
     * @param word Should contain the query word
     * @return Returns a List of website objects, that contain the given query word if found. If not
     * found it returns an empty List.
     */
    List<Website> lookup(String word);
}


