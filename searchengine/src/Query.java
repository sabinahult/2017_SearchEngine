import database.Website;
import index.Index;
import java.util.*;

public class Query {
    private List<Website> foundSites = new ArrayList<>();
    private List<Website> finalResult = new ArrayList<>();

    /**
     * Takes a string of words, and splits them around or. For each of the returned strings it splits them further around a
     * white space into an array of single words. Then for each of those single words it calls the lookup method of the
     * index, which returns a list of websites that contain the word. If the foundSites variable is empty, it adds all
     * elements from the lookup into the list of foundSites. If foundSites already has elements, it keeps only those
     * elements that are the same between the two collections, because we only want to return websites that contains
     * all words in the given part of the query. When all words in the given string has been looked up, it finally adds
     * all the found websites to finalResult. And then it does it all again for the next part of the query, if there
     * is more. This method can take any number of substrings of a query and any number of words in a substring.
     * @param query The input from the user passed from SearchEngine
     * @param index The database to search in for the given input passed from SearchEngine
     * @return A list of website objects that contain all of the given words in the query
     */
    public List<Website> runQuery(String query, Index index) {
        String queryLowerCase = query.toLowerCase();

        String[] queryLines = queryLowerCase.split(" or ");

        for (String line : queryLines) {
            String[] words = line.split(" ");

            //This boolean is to make sure, that if a word in a sequence is not found, then there's no need
            //to keep searching and an empty list should be returned
            boolean isFound = true;
            for(String word : words) {
                if(isFound) {
                    List<Website> tempList = index.lookup(word);

                    //If a word is not found there's no need to search for the next word, skip the rest and return
                    //empty list
                    if(tempList.isEmpty()) {
                        isFound = false;
                        foundSites.clear();
                    }

                    else {
                        //foundSites will be empty for the first word
                        if(foundSites.isEmpty()) {
                            foundSites.addAll(tempList);
                        } else {
                            foundSites.retainAll(tempList);
                        }
                    }
                }
            }

            finalResult.addAll(foundSites);
            foundSites.clear();
        }

        return finalResult;
    }
}