import java.util.List;
import java.util.ArrayList;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public class SimpleIndex implements Index {
    private List<Website> websites;
    private List<Website> relevantSites;


    @Override
    public void build(List<Website> listOfWebsites) {
        //websites = new ArrayList<>(listOfWebsites);// creates list everytime instead of assigning directly- below improved
        websites = listOfWebsites; // update the website we already created
        relevantSites = new ArrayList<>();

    }

    @Override
    public List<Website> lookup(String word) {
        // Go through all websites and return a list of websites that contain the query word or empty if no matches
        for (Website w : websites) {
            if (w.containsWord(word)) {
                relevantSites.add(w);
            }
        }
        return relevantSites;
    }
}
