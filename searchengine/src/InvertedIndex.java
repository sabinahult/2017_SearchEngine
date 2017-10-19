import java.util.*;

/**
 * Authors: Group M: Line, Lisa, Susan and Sabina
 */

public class InvertedIndex implements Index {
    private Map<String, List<Website>> websitesMap;

    @Override
    public void build(List<Website> listOfWebsites) {
        websitesMap = new HashMap<>(); // TreeMap or HashMap depending on wished implementation

        //Looking at each website in the dataset
        for (Website site : listOfWebsites) {

            //Looking at the wordslist for current website
            for (String word : site.getWords()) {

                //If the map does not contain the word as a key, then add the key and map to the current website
                if (!websitesMap.containsKey(word)) {
                    websitesMap.put(word, new ArrayList<>());
                    websitesMap.get(word).add(site);

                    //If the map contains the key already, check if the current website object is already in the list
                    // mapped to the key word. If not, then add it. If it is, do nothing.
                }

                else if(!websitesMap.get(word).contains(site)) {
                        websitesMap.get(word).add(site);
                    }
                }
            }
        }

    @Override
    public List<Website> lookup(String word) {
        if (websitesMap.containsKey(word)) {
            return websitesMap.get(word);

        //To avoid nullPointerException we're returning an empty ArrayList
        } else {
            return new ArrayList<>();
        }
    }
}

