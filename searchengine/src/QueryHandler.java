import database.Website;
import index.Index;
import java.util.*;

/**
 * Author: Group M: Sabina, Lisa, Line and Susan.
 * Overall function of the class is to query the database and return the result to SearchEngine.
 */

public class QueryHandler {


    /**
     * Takes a string of words (full query), and splits them around or into sub-queries. For each of the sub-queries
     * it splits them further around a white space into an array of single words. Then for each of those single words
     * it calls the lookup method of the index, which returns a list of websites that contains the word. If the
     * foundSites variable is empty, it stops looking for the next word in that sequence, and moves on to the first
     * word in the next sub-query (if there is one).
     *
     * If the word is found and the foundSites variable is empty, it adds all sites to foundSites. If foundSites already
     * has elements (from a previous iteration) it keeps only those elements that are the same between the two collections,
     * because we only want to return websites that contains all words in the given sub-query.
     *
     * When all words in a sub-query been looked up, it finally adds all the found websites to finalResult.
     * And then it does it all again for the next sub-query, if there is more.
     *
     * This method can take any number of sub-queries of a fullQuery split by or, and any number of words in a sub-query.
     * @param fullQuery The input from the user passed from SearchEngine
     * @param index The database to search in for the given input passed from SearchEngine
     * @return A list of website objects that contain all of the given words in one or more of the sub-queries, or an
     * empty list of none of the sub-queries are found.
     */
    public List<Website> runQuery(String fullQuery, Index index) {
        List<Website> foundSites = new ArrayList<>();
        List<Website> finalResult = new ArrayList<>();

        String fullQueryLowerCase = fullQuery.toLowerCase();

        String[] subQuery = fullQueryLowerCase.split("\\b\\s*or\\s*\\b");

        for (String line : subQuery) {
            String[] words = line.split("\\s+");

            //This boolean is to make sure, that if a word in a sequence is not found, then there's no need
            //to keep searching and an empty list should be returned because it's an all or nothing deal...
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

            //Trying to get rid of duplicates which occur in events where the same search word are on both sides
            //of an 'or'...
            for(Website site : foundSites)
                if(!finalResult.contains(site)) {
                finalResult.add(site);
                }

            foundSites.clear();
        }

        return finalResult;
    }
}