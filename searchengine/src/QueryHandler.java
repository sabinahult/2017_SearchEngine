import database.Website;
import index.Index;

import java.util.*;

/**
 * Author: Group M: Sabina, Lisa, Line and Susan.
 * Overall function of the class is to query the database and return the result to SearchEngine.
 */

public class QueryHandler {
    private Index index;


    public QueryHandler(Index index) {
        this.index = index;
    }

    /**
     * Takes a string of words (full query), and splits them around OR into sub-queries and passes that as an
     * Array to evaluateFullQuery and returns the result of that method.
     * @param fullQuery search words passed by the SearchEngine
     * return A list of relevant websites
     */
    public List<Website> getMatchingWebsites(String fullQuery) {
        String[] subQuery = fullQuery.split("\\b\\s*OR\\s*\\b");
        return evaluateFullQuery(subQuery);
    }

    /**
     * For each of the sub-queries it splits them around a white space into an array of single words.
     * When all words in a sub-query has been looked up, it finally adds all the found websites to finalResult.
     * And then it does it all again for the next sub-query, if there is more.
     * @param subQuery Array of strings passed by getMatchingWebsites
     * @return a List<Website> to getMatchingWebsites
     */
    private List<Website> evaluateFullQuery(String[] subQuery) {
        List<Website> finalResult = new ArrayList<>();

        for (String line : subQuery) {
            String[] words = line.split("\\s+");
            List<Website> foundSites = evaluateSubQuery(words);

            for (Website site : foundSites)
                if (!finalResult.contains(site)) {
                    finalResult.add(site);
                }
        }
        return finalResult;
    }

    /**
     * Takes an Array of strings and for each string calls the lookup method of the index, which returns a list of
     * websites that contains the word. If no matching websites are found the method breaks.
     *
     * If the word is found and the foundSites variable is empty, it adds all sites to foundSites. If foundSites already
     * has elements (from a previous iteration) it keeps only those elements that are the same between the two collections,
     * because we only want to return websites that contains all words in the given sub-query.
     * @param words Array of words received from evaluateFullQuery
     * @return a List<Website> that includes all the matching websites on one query
     */
    private List<Website> evaluateSubQuery(String[] words) {
        List<Website> foundSites = new ArrayList<>();

        for (String word : words) {
            word = word.toLowerCase();
            List<Website> tempList = index.lookup(word);

            //If a word is not found there's no need to search for the next word, skip the rest and return
            //empty list
            if (tempList.isEmpty()) {
                foundSites.clear();
                break;
            }

            //foundSites will be empty for the first word
            if (foundSites.isEmpty()) {
                foundSites.addAll(tempList);
            } else {
                foundSites.retainAll(tempList);
            }
        }
        return foundSites;
    }
}